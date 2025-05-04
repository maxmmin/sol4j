package io.github.maxmmin.sol.core.crypto.transaction.message;

import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.transaction.TransactionInstruction;

public class LegacyMessageBuilder extends MessageBuilder<Message> {
    @Override
    public LegacyMessageBuilder setBlockHash(String blockHash) {
        super.setBlockHash(blockHash);
        return this;
    }

    @Override
    public LegacyMessageBuilder setFeePayer(PublicKey feePayer) {
        super.setFeePayer(feePayer);
        return this;
    }

    @Override
    public LegacyMessageBuilder addInstruction(TransactionInstruction transactionInstruction) {
        super.addInstruction(transactionInstruction);
        return this;
    }

    @Override
    public LegacyMessageBuilder addInstructions(TransactionInstruction... transactionInstructions) {
        super.addInstructions(transactionInstructions);
        return this;
    }

    @Override
    protected Message build(MessageBuildArgs messageBuildArgs) {
        return new Message(messageBuildArgs.getMessageHeader(), messageBuildArgs.getAccountKeys(), getRecentBlockHash(), messageBuildArgs.getCompiledInstructions());
    }
}