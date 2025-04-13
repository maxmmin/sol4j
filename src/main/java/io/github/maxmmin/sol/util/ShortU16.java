package io.github.maxmmin.sol.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class ShortU16 {
    public static byte[] serialize(int uint16) {
        if (uint16 > Short.MAX_VALUE) throw new IllegalArgumentException("Value is too large");
        else if (uint16 < 0) throw new IllegalArgumentException("Value can not be negative");

        ByteBuffer buffer = ByteBuffer.allocate(3);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        int remVal = uint16;
        int cursor;
        for (cursor = 0; ; cursor++) {
            byte elem = (byte) (remVal & 0x7f);
            remVal >>= 7;
            if (remVal == 0) {
                buffer.put(cursor, elem);
                break;
            } else {
                elem |= (byte) 0x80;
                buffer.put(cursor, elem);
            }
        }

        return Arrays.copyOf(buffer.array(), cursor + 1);
    }
}
