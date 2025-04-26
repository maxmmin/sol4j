package io.github.maxmmin.sol.core.crypto.transaction;

import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.sign.MessageSigner;
import io.github.maxmmin.sol.core.crypto.transaction.message.Message;
import io.github.maxmmin.sol.core.crypto.transaction.message.MessageSerializer;

import java.util.List;

public class LegacyTransactionBuilder extends TransactionBuilder<Transaction, Message> {
    public LegacyTransactionBuilder(Message message) {
        this(message, MessageSigner.getSigner());
    }

    public LegacyTransactionBuilder(Message message, MessageSigner<Message> messageSigner) {
        super(message, MessageSerializer.getSerializer().serialize(message), messageSigner);
    }

    @Override
    protected Transaction build(TransactionBuilder<Transaction, Message>.TransactionComponents transactionComponents) {
        return new Transaction(transactionComponents.getSignatures(), transactionComponents.getMessage());
    }

    @Override
    protected List<PublicKey> getSigners(Message message) {
        return message.getAccountKeys().subList(0, Byte.toUnsignedInt(message.getMessageHeader().getNumRequiredSignatures()));
    }
}
