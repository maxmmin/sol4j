package io.github.maxmmin.sol.core.client.request;

import io.github.maxmmin.sol.core.client.exception.RpcException;

import java.util.List;

public interface BatchedRequest<R> {
    List<R> send() throws RpcException;
}
