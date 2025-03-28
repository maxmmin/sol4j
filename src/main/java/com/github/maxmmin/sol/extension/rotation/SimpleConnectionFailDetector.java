package com.github.maxmmin.sol.extension.rotation;

import com.github.maxmmin.sol.core.exception.RpcException;
import com.github.maxmmin.sol.core.exception.RpcUnavailableException;

import java.util.List;

public class SimpleConnectionFailDetector implements ConnectionFailDetector {
    private final List<String> markers;

    public SimpleConnectionFailDetector(List<String> markers) {
        this.markers = markers;
    }

    public SimpleConnectionFailDetector() {
        this.markers = List.of("429", "rate", "limit", "exceeded");
    }

    @Override
    public boolean isConnectionFail(RpcException exception) {
        if (exception instanceof RpcUnavailableException) return true;
        String message = exception.getMessage();
        for (String marker : markers) {
            if (message.contains(marker)) return true;
        }
        return false;
    }
}
