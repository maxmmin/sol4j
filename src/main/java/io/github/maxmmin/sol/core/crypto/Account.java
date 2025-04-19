package io.github.maxmmin.sol.core.crypto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.nio.ByteBuffer;
import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public class Account {
    private final byte[] publicKey;
    private final byte[] secretKey;

    public Account(byte[] privateKey) {
        if (privateKey.length != 64) throw new IllegalArgumentException("Private key must be 64 bytes");
        ByteBuffer buffer = ByteBuffer.allocate(privateKey.length);

        byte[] pubKey = new byte[32];
        buffer.get(pubKey, 0, 32);
        this.publicKey = pubKey;

        byte[] secretKey = new byte[32];
        buffer.get(secretKey, 0, 32);
        this.secretKey = secretKey;
    }

    public PublicKey getPublicKey() {
        return new PublicKey(publicKey);
    }

    public byte[] getSecretKey() {
        return Arrays.copyOfRange(secretKey, 0, 32);
    }

    public static Account fromSecretKey (byte[] secretKey) {
        byte[] publicKey = PrivateKeyGen.fromSecretKey(secretKey);
        byte[] privateKey = new byte[64];
        System.arraycopy(secretKey, 0, privateKey, 0, secretKey.length);
        System.arraycopy(publicKey, 0, publicKey, 0, publicKey.length);
        return new Account(privateKey);
    }
}
