package org.mxmn.sol.extension;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mxmn.sol.core.client.HttpRpcGateway;
import org.mxmn.sol.core.client.RpcGateway;

import java.util.Set;
import java.util.function.Function;

public class RpcGatewayFactory {
    public static RpcGateway create(String endpoint) {
        return extractScheme(endpoint).constructClient(endpoint);
    }

    protected static Scheme extractScheme(String endpoint) {
        String protocol = endpoint.substring(0, endpoint.indexOf(":"));
        return Scheme.forValue(protocol);
    }

    @Getter
    @RequiredArgsConstructor
    protected enum Scheme {
        HTTP(Set.of("http", "https"), endpoint ->
            HttpRpcGateway.create(endpoint, 15_000, 120_000, 30_000)
        )
        ;
        private final Set<String> schemeNames;
        private final Function<String, RpcGateway> factoryMethod;

        public RpcGateway constructClient(String endpoint) {
            return factoryMethod.apply(endpoint);
        }

        public static Scheme forValue(String value) {
            for (Scheme scheme: values()) {
                if (scheme.getSchemeNames().contains(value)) return scheme;
            }
            throw new IllegalArgumentException("Unknown scheme detected: " + value);
        }
    }
}
