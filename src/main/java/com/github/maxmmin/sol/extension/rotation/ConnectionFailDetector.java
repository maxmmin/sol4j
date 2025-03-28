package com.github.maxmmin.sol.extension.rotation;

import com.github.maxmmin.sol.core.exception.RpcException;

public interface ConnectionFailDetector {
    boolean isConnectionFail(RpcException exception);
}
