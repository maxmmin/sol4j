package io.github.maxmmin.sol.core.crypto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.maxmmin.sol.core.client.serialization.PublicKeyJsonSerializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@JsonSerialize(using = PublicKeyJsonSerializer.class)
public class PublicKey {
    private final byte[] bytes;

    public PublicKey(byte[] publicKey) {
        if (publicKey.length != 32) throw new IllegalArgumentException("Public key must be 32 bytes");
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
        return Arrays.equals(bytes, publicKey.bytes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bytes);
    }

    @Override
    public String toString() {
        return toBase58();
    }

    public static PublicKey createWithSeed(PublicKey publicKey, String seed, PublicKey platformId) {
        try (ByteArrayOutputStream bytes = new ByteArrayOutputStream()) {
            bytes.write(publicKey.getBytes());

            if (seed.length() < 32) bytes.write(seed.getBytes());
            else throw new IllegalArgumentException("Seed length must be less than 32 bytes");

            bytes.write(platformId.getBytes());

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(bytes.toByteArray());
            return new PublicKey(hash);
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException("An error occurred while creating public key", e);
        }
    }

    public static PublicKey fromBase58(String base58PublicKey) {
        return new PublicKey(Base58.decodeFromString(base58PublicKey));
    }

    public static PublicKey fromBase64(String base64PublicKey) {
        return new PublicKey(Base64.getDecoder().decode(base64PublicKey));
    }
}
