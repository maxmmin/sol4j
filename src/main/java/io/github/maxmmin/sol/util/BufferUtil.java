package io.github.maxmmin.sol.util;

import io.github.maxmmin.sol.core.crypto.PublicKey;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BufferUtil {
    public static ByteBuffer putBytes(ByteBuffer buffer, int offset, byte[] bytes) {
        int cursor = offset;
        for (byte b: bytes) {
            buffer.put(cursor++, b);
        }
        return buffer;
    }

    public static ByteBuffer putPubkey(ByteBuffer buffer, int offset, PublicKey publicKey) {
        return putBytes(buffer, offset, publicKey.getBytes());
    }

    public static ByteBuffer allocateLE(int capacity) {
        return ByteBuffer.allocate(capacity).order(ByteOrder.LITTLE_ENDIAN);
    }
}
