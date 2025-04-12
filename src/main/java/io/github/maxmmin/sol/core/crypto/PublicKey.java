package io.github.maxmmin.sol.core.crypto;

import io.github.maxmmin.sol.util.Base58;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

public class PublicKey {
    private final byte[] bytes;

    protected PublicKey(byte[] publicKey) {
        this.bytes = publicKey;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public String toBase58() {
        return Base58.encodeToString(bytes);
    }

    public String toBase64() {
        return Base64.getEncoder().encodeToString(bytes);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PublicKey)) return false;
        PublicKey publicKey = (PublicKey) o;
        return Objects.deepEquals(bytes, publicKey.bytes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bytes);
    }

    public static PublicKey create(byte[] publicKey) {
        if (publicKey.length != 32) throw new IllegalArgumentException("Public key must be 32 bytes");
        return new PublicKey(publicKey);
    }

    public static PublicKey createWithSeed(PublicKey publicKey, String seed, PublicKey platformId) {
        try (ByteArrayOutputStream bytes = new ByteArrayOutputStream()) {
            bytes.write(publicKey.getBytes());

            if (seed.length() < 32) bytes.write(seed.getBytes());
            else throw new IllegalArgumentException("Seed length must be less than 32 bytes");

            bytes.write(platformId.getBytes());

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(bytes.toByteArray());
            return create(hash);
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException("An error occurred while creating public key", e);
        }
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
