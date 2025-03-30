package com.github.maxmmin.sol.core.client.request;

import com.github.maxmmin.sol.core.exception.RpcException;

import java.util.List;

public interface BatchedRequest<R> {
    List<R> send() throws RpcException;
}
