package io.github.maxmmin.sol.core.client.request;

import io.github.maxmmin.sol.core.exception.RpcException;
import io.github.maxmmin.sol.core.type.request.RpcRequest;

public interface Request<R> {
    RpcRequest construct();
    R send() throws RpcException;
}
