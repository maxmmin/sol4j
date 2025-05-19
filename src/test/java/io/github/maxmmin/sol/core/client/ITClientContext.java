package io.github.maxmmin.sol.core.client;

import io.github.maxmmin.sol.core.client.gateway.HttpRpcGateway;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Objects;

public class ITClientContext implements BeforeAllCallback {
    private static boolean initialized = false;

    private static RpcClient rpcClient;
    private static RequestThrottler requestThrottler;

    public static RpcClient getRpcClient() {
        return Objects.requireNonNull(rpcClient);
    }

    public static RequestThrottler getRequestThrottler() {
        return Objects.requireNonNull(requestThrottler);
    }

    @Override
    public synchronized void beforeAll(ExtensionContext context) {
        if (!initialized) {
            initRpcClient();
            initRequestThrottler();
            initialized = true;
        }
    }

    private void initRpcClient() {
        rpcClient = new SimpleRpcClient(HttpRpcGateway.create(loadRpcUrl()));
    }

    private String loadRpcUrl() {
        return "https://api.mainnet-beta.solana.com";
    }

    private void initRequestThrottler() {
        requestThrottler = new RequestThrottler(loadClientRPS());
    }

    private int loadClientRPS() {
        return 1;
    }
}
