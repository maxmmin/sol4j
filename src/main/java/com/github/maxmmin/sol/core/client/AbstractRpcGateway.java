package com.github.maxmmin.sol.core.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.maxmmin.sol.util.Collector;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import com.github.maxmmin.sol.core.exception.RpcBatchedResponseException;
import com.github.maxmmin.sol.core.exception.RpcException;
import com.github.maxmmin.sol.core.exception.RpcResponseException;
import com.github.maxmmin.sol.core.type.request.Param;
import com.github.maxmmin.sol.core.type.request.RpcRequest;
import com.github.maxmmin.sol.core.type.response.RpcResponse;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

class ParamSerializer extends JsonSerializer<Param> {
    @Override
    public void serialize(Param param, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (param.getValue() != null) serializerProvider.defaultSerializeValue(param.getValue(), jsonGenerator);
        else jsonGenerator.writeNull();
    }
}

@Slf4j
public abstract class AbstractRpcGateway implements RpcGateway {
    @Getter
    private final String endpoint;
    private final ObjectMapper objectMapper;

    protected AbstractRpcGateway(String endpoint) {
        this.endpoint = endpoint;
        this.objectMapper = createMapper();
    }

    private static ObjectMapper createMapper() {
        SimpleModule simpleModule = new SimpleModule().addSerializer(Param.class, new ParamSerializer());

        return new ObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .registerModule(simpleModule)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    protected void checkIfSuccessful(RpcResponse<?>rpcResponse) throws RpcResponseException {
        if (rpcResponse.getError() != null) throw new RpcResponseException(rpcResponse);
    }

    protected void checkIfSuccessful(Collection<? extends RpcResponse<?>> rpcResponses) throws RpcBatchedResponseException {
        List<RpcResponse<?>>errors = new ArrayList<>();
        for (RpcResponse<?> response: rpcResponses) {
            if (response.getError() != null) errors.add(response);
        }
        if (!errors.isEmpty()) throw new RpcBatchedResponseException(errors);
    }

    @Override
    public <V> RpcResponse<V> send(RpcRequest rpcRequest, TypeReference<V> typeRef) throws RpcException {
        RpcRequestDefinition request = new RpcRequestDefinition(rpcRequest, this::toJson);
        ParameterizedType type = constructSingleResponseType(typeRef);
        RpcResponse<V> response = sendRaw(request, type);
        checkIfSuccessful(response);
        return response;
    }

    @Override
    public <V> void sendBatched(List<RpcRequest>requests, TypeReference<V> typeRef, Map<String, RpcResponse<V>> target) throws RpcException {
        RpcRequestDefinition requestDefinition = new RpcRequestDefinition(requests, this::toJson);
        ParameterizedType responseType = constructSingleResponseType(typeRef);
        ParameterizedType arrayResponseType = constructBatchingResponseType(responseType);
        List<RpcResponse<V>> results = sendRaw(requestDefinition, arrayResponseType);
        checkIfSuccessful(results);
        results.forEach(r -> target.put(r.getId(), r));
    }

    @Override
    public <V> List<RpcResponse<V>> sendBatched(List<RpcRequest> requests, TypeReference<V> typeReference) throws RpcException {
        Map<String, RpcResponse<V>> resultMap = new ConcurrentHashMap<>();
        sendBatched(requests, typeReference, resultMap);
        return Collector.collectResultsOrdered(requests, resultMap);
    }

    protected Map<String, Integer> countMethods(Collection<RpcRequest>requests) {
        return new HashMap<String, Integer>() {{
            requests.forEach(r -> {
                String method = r.getMethod();
                int counter = getOrDefault(method, 0);
                put(method, counter + 1);
            });
        }};
    }

    protected <V> V sendRaw(RpcRequestDefinition requestDefinition, Type responseType) throws RpcException {
        String requestDescription = requestDefinition.isBatched() ?
                getDescription(countMethods(requestDefinition.getRequests()), getEndpoint()) :
                getDescription(requestDefinition.getRequests().get(0).getMethod(), getEndpoint());
        log.trace(requestDescription);
        byte[] responseBytes = doRequest(requestDefinition);
        return fromJson(responseBytes, responseType);
    }

    protected abstract byte[] doRequest (RpcRequestDefinition requestDefinition) throws RpcException;

    protected String getDescription(String method, String endpoint) {
        return String.format("%s -> [%s]", method, endpoint);
    }

    protected String getDescription(Map<String, Integer> methods, String endpoint) {
        StringBuilder description = new StringBuilder();
        methods.forEach((method, value) -> {
            if (description.length() == 0) description.append(", ");
            description.append(String.format("%sx%s", method, value));
        });
        return String.format("[%s] -> [%s]", description, endpoint);
    }

    protected ParameterizedType constructBatchingResponseType(ParameterizedType responseType) {
        return new ParameterizedType() {
            @NotNull
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[] {responseType};
            }

            @NotNull
            @Override
            public Type getRawType() {
                return List.class;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
    }

    protected <V> ParameterizedType constructSingleResponseType(TypeReference<V>typeRef) {
        return new ParameterizedType() {
            @Override
            public @NotNull Type[] getActualTypeArguments() {
                return new Type[] {typeRef.getType()};
            }

            @NotNull
            @Override
            public Type getRawType() {
                return RpcResponse.class;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
    }

    protected byte[] toJson(Object o) {
        try {
            return objectMapper.writeValueAsBytes(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected <T> T fromJson(byte[] jsonBytes, Type type) throws RpcException {
        JavaType javaType = objectMapper.constructType(type);
        try {
            return objectMapper.readValue(jsonBytes, javaType);
        } catch (IOException e) {
            String error;
            Optional<String>rpcError = tryGetError(jsonBytes);
            if (rpcError.isPresent()) error = rpcError.get();
            else {
                String body = new String(jsonBytes);
                if (body.length() > 128) body = body.substring(0, 128)+"...";
                error = String.format("An error was thrown during response deserialization.\n\tTarget type: %s\n\tPresent body:\n\t%s{}",  javaType, body);
            }

            throw new RpcException(error);
        }
    }

    protected Optional<String> tryGetError(byte[]bytes) {
        try {
            RpcResponse<?>response = objectMapper.readValue(bytes, RpcResponse.class);
            if (response.getError() != null) return Optional.of(objectMapper.writeValueAsString(response.getError()));
        } catch (Exception ignored) {}

        return Optional.empty();
    }

    protected static class RpcRequestDefinition {
        private final List<RpcRequest> requests;
        private final Function<Object, byte[]> serializer;
        private final boolean batched;

        public RpcRequestDefinition(Collection<RpcRequest> requests, Function<Object, byte[]> serializer) {
            this.requests = List.copyOf(requests);
            this.serializer = serializer;
            batched = true;
        }

        public RpcRequestDefinition(RpcRequest request, Function<Object, byte[]> serializer) {
            this.requests = List.of(request);
            this.serializer = serializer;
            batched = false;
        }

        public List<RpcRequest> getRequests() {
            return requests;
        }

        public Object getBody() {
            return batched ? requests : requests.get(0);
        }

        public String toJsonString() {
            return new String(toJsonBytes());
        }

        public byte[] toJsonBytes() {
            return serializer.apply(getBody());
        }

        public boolean isBatched() {
            return batched;
        }
    }

}
