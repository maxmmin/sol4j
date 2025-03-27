package com.github.maxmmin.sol.core.client.request;

import com.github.maxmmin.sol.core.exception.RpcException;

/**
 *
 * @param <D> - Default Response Type
 * @param <B> - Base Enc Response Type
 * @param <J> - JSON Response Type
 * @param <P> - JSON Parsed Response Type
 */
interface RpcVariety<D, B, J, P> {
    D noarg() throws RpcException;
    B base58() throws RpcException, UnsupportedOperationException;
    B base64() throws RpcException, UnsupportedOperationException;
    J json() throws RpcException, UnsupportedOperationException;
    P jsonParsed() throws RpcException, UnsupportedOperationException;
}
