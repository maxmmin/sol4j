package io.github.maxmmin.sol.core.crypto.transaction;

import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.sign.MessageSigner;
import io.github.maxmmin.sol.core.crypto.transaction.message.*;
import io.github.maxmmin.sol.core.crypto.transaction.message.MessageSerializer;

import java.util.List;

public class TransactionV0Builder extends TransactionBuilder<TransactionV0, MessageV0> {
    public TransactionV0Builder(MessageV0 message) {
        this(message, MessageSigner.getSignerV0());
    }

    public TransactionV0Builder(MessageV0 message, MessageSigner<MessageV0> messageSigner) {
        super(message, MessageSerializer.getSerializerV0().serialize(message), messageSigner);
    }

    @Override
    protected TransactionV0 build(TransactionBuilder<TransactionV0, MessageV0>.TransactionComponents transactionComponents) {
        return new TransactionV0(transactionComponents.getSignatures(), transactionComponents.getMessage());
    }

    @Override
    protected List<PublicKey> getSigners(MessageV0 message) {
        return message.getAccountKeys().subList(0, Byte.toUnsignedInt(message.getMessageHeader().getNumRequiredSignatures()));
    }
}
