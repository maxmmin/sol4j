package io.github.maxmmin.sol.core.gateway;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.maxmmin.sol.core.exception.RpcBatchedResponseException;
import io.github.maxmmin.sol.core.exception.RpcException;
import io.github.maxmmin.sol.core.exception.RpcResponseException;
import io.github.maxmmin.sol.core.type.request.RpcRequest;
import io.github.maxmmin.sol.core.type.response.RpcResponse;
import io.github.maxmmin.sol.util.Collector;
import io.github.maxmmin.sol.util.Types;
import lombok.Getter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public abstract class AbstractRpcGateway implements RpcGateway {
    @Getter
    private final String endpoint;
    private final ObjectMapper objectMapper;

    protected AbstractRpcGateway(String endpoint) {
        this.endpoint = endpoint;
        this.objectMapper = createMapper();
    }

    private static ObjectMapper createMapper() {
        return new ObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
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
        Type type = constructRpcResponseType(typeRef);
        RpcResponse<V> response = sendRaw(request, type);
        checkIfSuccessful(response);
        return response;
    }

    @Override
    public <V> void sendBatched(List<RpcRequest>requests, TypeReference<V> typeRef, Map<String, RpcResponse<V>> target) throws RpcException {
        RpcRequestDefinition requestDefinition = new RpcRequestDefinition(requests, this::toJson);
        Type responseType = constructBatchedRpcResponseType(typeRef);
        List<RpcResponse<V>> results = sendRaw(requestDefinition, responseType);
        checkIfSuccessful(results);
        results.forEach(r -> target.put(r.getId(), r));
    }

    @Override
    public <V> List<RpcResponse<V>> sendBatched(List<RpcRequest> requests, TypeReference<V> typeReference) throws RpcException {
        Map<String, RpcResponse<V>> resultMap = new ConcurrentHashMap<>();
        sendBatched(requests, typeReference, resultMap);
        return Collector.collectResultsOrdered(requests, resultMap);
    }

    protected <V> V sendRaw(RpcRequestDefinition requestDefinition, Type responseType) throws RpcException {
        byte[] responseBytes = doRequest(requestDefinition);
        return fromJson(responseBytes, responseType);
    }

    protected abstract byte[] doRequest (RpcRequestDefinition requestDefinition) throws RpcException;

    protected <V> Type constructBatchedRpcResponseType(TypeReference<V> singleResponseTypeRef) {
        return Types.toParametrizedList(constructRpcResponseType(singleResponseTypeRef));
    }
    
    protected JavaType constructRpcResponseType(TypeReference<?> typeRef) {
        return Types.toParametrizedType(RpcResponse.class, typeRef);
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
