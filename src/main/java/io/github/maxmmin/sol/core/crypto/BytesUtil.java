package io.github.maxmmin.sol.core.crypto;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BytesUtil {
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

    public static ByteBuffer putUint64(ByteBuffer buffer, int offset, BigInteger value) {
        return buffer.putLong(offset, uint64ToLong(value));
    }

    public static ByteBuffer allocateLE(int capacity) {
        return ByteBuffer.allocate(capacity).order(ByteOrder.LITTLE_ENDIAN);
    }

    public static byte[] serializeUint64LE(BigInteger uint64) {
        return BytesUtil.allocateLE(8).putLong(uint64ToLong(uint64)).array();
    }

    private static long uint64ToLong(BigInteger uint64) {
        byte[] bytes = uint64.toByteArray();
        int uintLength = bytes[0] == 0 ? bytes.length - 1 : bytes.length;
        if (uintLength > 8) throw new IllegalArgumentException("Number is too large for uint64");
        return uint64.longValue();
    }
}
