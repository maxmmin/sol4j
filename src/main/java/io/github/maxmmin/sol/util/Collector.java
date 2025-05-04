package io.github.maxmmin.sol.util;

import io.github.maxmmin.sol.core.client.type.request.RpcRequest;
import io.github.maxmmin.sol.core.client.type.response.RpcResponse;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Collector {
    public static <V> List<RpcResponse<V>> collectResultsOrdered(List<RpcRequest> requests, Map<String, RpcResponse<V>> responses) {
        Map<String, Integer>positions = new HashMap<>();
        int idx = 0;
        for (RpcRequest r : requests) {
            positions.put(r.getId(), idx++);
        }
        @SuppressWarnings("unchecked") RpcResponse<V>[]results = new RpcResponse[responses.size()];
        responses.forEach((key, value) -> {
            results[positions.get(key)] = value;
        });
        return Arrays.asList(results);
    }
}
