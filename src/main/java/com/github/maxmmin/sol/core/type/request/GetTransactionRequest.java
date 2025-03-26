package com.github.maxmmin.sol.core.type.request;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.type.response.tx.base.BaseEncTransaction;
import com.github.maxmmin.sol.core.type.response.tx.json.JsonTransaction;
import com.github.maxmmin.sol.core.type.response.tx.jsonparsed.JsonParsedTransaction;
import org.jetbrains.annotations.Nullable;

public class GetTransactionRequest extends ExecRequest<BaseEncTransaction, BaseEncTransaction, JsonTransaction, JsonParsedTransaction> {
    public GetTransactionRequest(RpcGateway gateway, String signature, GetTransactionConfig config) {
        super(gateway, new TypeReference<BaseEncTransaction>() {}, new TypeRefere, jsonType, jsonParsedType);
    }

    @Override
    protected RpcRequest constructRequest(Encoding encoding) {
        return null;
    }
}
