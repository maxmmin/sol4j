package io.github.maxmmin.sol.core.crypto;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class ShortU16 {
    private final byte[] value;

    public ShortU16(byte[] value) {
        if (value.length > 3) throw new IllegalArgumentException("Value too large for ShortU16");
        this.value = value;
    }

    public int getBytesCount() {
        return value.length;
    }

    public byte[] getValue() {
        return value;
    }

    public static ShortU16 valueOf(int uint16) {
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

        byte[] data = Arrays.copyOf(buffer.array(), cursor + 1);
        return new ShortU16(data);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ShortU16)) return false;
        ShortU16 shortU16 = (ShortU16) o;
        return Arrays.equals(value, shortU16.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }
}
