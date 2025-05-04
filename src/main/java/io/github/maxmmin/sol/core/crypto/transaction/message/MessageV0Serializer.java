package io.github.maxmmin.sol.core.crypto.transaction.message;

import java.nio.ByteBuffer;

public class MessageV0Serializer implements MessageSerializer<MessageV0> {
    private final static byte VERSION_PREFIX = (byte) (1 << 7);

    private final MessageV0ComponentsSerializer messageComponentsSerializer;

    public MessageV0Serializer() {
        this(new MessageV0ComponentsSerializer());
    }

    public MessageV0Serializer(MessageV0ComponentsSerializer messageComponentsSerializer) {
        this.messageComponentsSerializer = messageComponentsSerializer;
    }

    @Override
    public byte[] serialize(MessageV0 message) {
        byte[] serializedHeader = messageComponentsSerializer.serializeMessageHeader(message.getMessageHeader());
        byte[] serializedKeys = messageComponentsSerializer.serializeAccountKeys(message.getAccountKeys());
        byte[] serializedBlockHash = messageComponentsSerializer.serializeBlockHash(message.getRecentBlockhash());
        byte[] serializedInstructions = messageComponentsSerializer.serializeInstructions(message.getInstructions());
        byte[] serializedLookupTables = messageComponentsSerializer.serializeAddressLookupTables(message.getAccountKeysLookups());

        int size = serializedHeader.length + serializedKeys.length + serializedBlockHash.length + serializedInstructions.length + serializedLookupTables.length;
        ByteBuffer buffer = ByteBuffer.allocate(1 + size);
        buffer.put(VERSION_PREFIX);
        buffer.put(serializedHeader);
        buffer.put(serializedKeys);
        buffer.put(serializedBlockHash);
        buffer.put(serializedInstructions);
        buffer.put(serializedLookupTables);
        return buffer.array();
    }
}
