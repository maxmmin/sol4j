package io.github.maxmmin.sol.core.crypto;

import io.github.maxmmin.sol.util.Base58;

import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

public class PublicKey {
    private final byte[] value;

    protected PublicKey(byte[] publicKey) {
        this.value = publicKey;
    }

    public String toBase58() {
        return Base58.encodeToString(value);
    }

    public String toBase64() {
        return Base64.getEncoder().encodeToString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PublicKey)) return false;
        PublicKey publicKey = (PublicKey) o;
        return Objects.deepEquals(value, publicKey.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }

    public static PublicKey create(byte[] publicKey) {
        if (publicKey.length != 32) throw new IllegalArgumentException("Public key must be 32 bytes");
        return new PublicKey(publicKey);
    }

    public static PublicKey fromBase58(String base58PublicKey) {
        byte[] base58Bytes = base58PublicKey.getBytes();
        return create(Base58.decode(base58Bytes));
    }

    public static PublicKey fromBase64(String base64PublicKey) {
        byte[] base64Bytes = base64PublicKey.getBytes();
        return create(Base64.getDecoder().decode(base64Bytes));
    }
}
