package org.mxmn.sol.extension;

import lombok.Data;

import java.util.List;

@Data
public class RpcConfig {
    private List<RpcEndpoint> endpoints;

    @Data
    public static class RpcEndpoint {
        private String url;
        private int priority = 5;
        private int batchSize = -1;

        private RpcEndpointRateLimit[] rateLimits = new RpcEndpointRateLimit[0];
        private RpcFeature[] features = new RpcFeature[0];
    }

    @Data
    public static class RpcFeature {
        private String name;
    }

    @Data
    public static class RpcEndpointRateLimit {
        private int requestsPerInterval = 1;
        private int intervalMs;
        private int capacity;
    }
}
