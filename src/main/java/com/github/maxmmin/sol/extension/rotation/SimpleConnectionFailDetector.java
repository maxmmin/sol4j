package com.github.maxmmin.sol.extension.rotation;

import com.github.maxmmin.sol.core.exception.RpcException;

public class SimpleConnectionFailDetector implements ConnectionFailDetector {
    @Override
    public boolean isConnectionFail(RpcException exception) {
        return true;
    }
}
