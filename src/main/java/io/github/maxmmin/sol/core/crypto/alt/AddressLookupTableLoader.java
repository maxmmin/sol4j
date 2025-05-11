package io.github.maxmmin.sol.core.crypto.alt;

import io.github.maxmmin.sol.core.client.RpcClient;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.type.request.GetAccountInfoConfig;
import io.github.maxmmin.sol.core.client.type.response.ContextWrapper;
import io.github.maxmmin.sol.core.client.type.response.account.base.BaseEncAccount;
import io.github.maxmmin.sol.core.crypto.PublicKey;

import java.util.Base64;

public class AddressLookupTableLoader {
    private final RpcClient rpcClient;
    private final AddressLookupTableAccountDeserializer deserializer;

    public AddressLookupTableLoader(RpcClient rpcClient) {
        this(rpcClient, new AddressLookupTableAccountDeserializer());
    }

    public AddressLookupTableLoader(RpcClient rpcClient, AddressLookupTableAccountDeserializer deserializer) {
        this.rpcClient = rpcClient;
        this.deserializer = deserializer;
    }

    public AddressLookupTableAccountWithContext load(String publicKey) throws RpcException {
        return load(publicKey, GetAccountInfoConfig.empty());
    }

    public AddressLookupTableAccountWithContext load(String publicKey, GetAccountInfoConfig cfg) throws RpcException {
        PublicKey pubKey = PublicKey.fromBase58(publicKey);

        ContextWrapper<BaseEncAccount> accountResponse = rpcClient.getAccountInfo(publicKey, cfg).base64();
        BaseEncAccount account = accountResponse.getValue();

        AddressLookupTableAccount tableAccount = null;
        if (account != null) {
            byte[] dataBytes = Base64.getDecoder().decode(account.getData().get(0));
            AddressLookupTableState state = deserializer.deserialize(dataBytes);
            tableAccount = new AddressLookupTableAccount(pubKey, state);
        }

        return new AddressLookupTableAccountWithContext(accountResponse.getContext(), tableAccount);
    }

}
