package io.github.maxmmin.sol.core.crypto.transaction.message;

import java.nio.ByteBuffer;

public class LegacyMessageSerializer implements MessageSerializer<Message> {
    private final MessageComponentsSerializer messageComponentsSerializer;

    public LegacyMessageSerializer() {
        this(new MessageComponentsSerializer());
    }

    public LegacyMessageSerializer(MessageComponentsSerializer messageComponentsSerializer) {
        this.messageComponentsSerializer = messageComponentsSerializer;
    }

    @Override
    public byte[] serialize(Message message) {
        byte[] serializedHeader = messageComponentsSerializer.serializeMessageHeader(message.getMessageHeader());
        byte[] serializedKeys = messageComponentsSerializer.serializeAccountKeys(message.getAccountKeys());
        byte[] serializedBlockHash = messageComponentsSerializer.serializeBlockHash(message.getRecentBlockhash());
        byte[] serializedInstructions = messageComponentsSerializer.serializeInstructions(message.getInstructions());

        int size = serializedHeader.length + serializedKeys.length + serializedBlockHash.length + serializedInstructions.length;
        ByteBuffer buffer = ByteBuffer.allocate(size);
        buffer.put(serializedHeader);
        buffer.put(serializedKeys);
        buffer.put(serializedBlockHash);
        buffer.put(serializedInstructions);
        return buffer.array();
    }
}
