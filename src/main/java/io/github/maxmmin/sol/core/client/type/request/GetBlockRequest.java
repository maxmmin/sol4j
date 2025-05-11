package io.github.maxmmin.sol.core.client.type.request;

import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.enc.MultiEncRequest;
import io.github.maxmmin.sol.core.client.type.response.block.BaseEncBlock;
import io.github.maxmmin.sol.core.client.type.response.block.JsonBlock;
import io.github.maxmmin.sol.core.client.type.response.block.JsonParsedBlock;

public class GetBlockRequest extends MultiEncRequest<JsonBlock, BaseEncBlock, JsonBlock, JsonParsedBlock> {
    public GetBlockRequest(EncodingSupport encodingSupport, RpcGateway gateway) {
        super(
                new RpcTypes<JsonBlock, BaseEncBlock, JsonBlock, JsonParsedBlock>() {},
                EncodingSupport.full(),
                gateway
        );
    }

    @Override
    protected RpcRequest construct(Encoding encoding) {
        return null;
    }
}
