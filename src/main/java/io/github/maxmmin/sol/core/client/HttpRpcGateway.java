package io.github.maxmmin.sol.core.client;

import io.github.maxmmin.sol.core.exception.RpcUnavailableException;
import okhttp3.*;
import io.github.maxmmin.sol.core.exception.RpcException;

import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HttpRpcGateway extends AbstractRpcGateway implements RpcGateway {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final OkHttpClient httpClient;
    private final boolean retryOnEmptyBody = true;

    public HttpRpcGateway(String endpoint, OkHttpClient httpClient) {
        super(endpoint);
        this.httpClient = httpClient;
    }

    @Override
    protected byte[] doRequest(RpcRequestDefinition requestDefinition) throws RpcException {
        return doRequestInternal(requestDefinition, retryOnEmptyBody);
    }

    protected byte[] doRequestInternal(RpcRequestDefinition requestDefinition, boolean retryOnEmpty) throws RpcException {
        try (Response response = this.httpClient.newCall(buildRequest(requestDefinition)).execute()) {
            ResponseBody body = response.body();
            if (body == null) throw new NullPointerException("Response body is null");
            return body.bytes();
        } catch (SSLHandshakeException e) {
            throw new RpcUnavailableException("SSL Handshake failed: " + e.getMessage());
        } catch (IOException e) {
            throw new RpcUnavailableException("IO error during RPC call: " + e.getMessage());
        } catch (NullPointerException e) {
            if (retryOnEmpty) return doRequestInternal(requestDefinition, false);
            else throw new RpcException("Empty response received: " + e.getMessage());
        }
    }

    protected Request buildRequest(RpcRequestDefinition definition) {
        RequestBody body = RequestBody.create(definition.toJsonBytes(), JSON);

        return (new Request.Builder())
                .url(getEndpoint())
                .post(body)
                .build();
    }

    public static HttpRpcGateway create(String endpoint) {
        return new HttpRpcGateway(endpoint, (new OkHttpClient.Builder()).readTimeout(20L, TimeUnit.SECONDS).build());
    }

    public static HttpRpcGateway create(String endpoint, String userAgent) {
        return new HttpRpcGateway(
                endpoint,
                new OkHttpClient.Builder()
                        .addNetworkInterceptor(
                                (chain) -> chain.proceed(
                                        chain
                                                .request()
                                                .newBuilder()
                                                .header("User-Agent", userAgent)
                                                .build()
                                )
                        )
                        .readTimeout(20L, TimeUnit.SECONDS)
                        .build()
        );
    }

    public static HttpRpcGateway create(String endpoint, int connectTimeoutMs, int readTimeoutMs, int writeTimeoutMs) {
        return new HttpRpcGateway(
                endpoint,
                new OkHttpClient.Builder()
                        .connectTimeout(connectTimeoutMs, TimeUnit.MILLISECONDS)
                        .readTimeout(readTimeoutMs, TimeUnit.MILLISECONDS)
                        .writeTimeout(writeTimeoutMs, TimeUnit.MILLISECONDS)
                        .build()
        );
    }
}
