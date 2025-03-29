package com.github.maxmmin.sol.core.client.request;

import com.github.maxmmin.sol.core.exception.RpcException;

public interface Request<R> {
    R send() throws RpcException;
}
