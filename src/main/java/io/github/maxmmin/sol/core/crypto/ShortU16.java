package io.github.maxmmin.sol.core.crypto;

import lombok.Getter;

import java.util.Arrays;

@Getter
public class ShortU16 {
    private final byte[] value;

    public ShortU16(byte[] value) {
        this.value = value;
    }

    public int getBytesCount() {
        return value.length;
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
