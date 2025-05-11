package io.github.maxmmin.sol.core.crypto;

import lombok.Getter;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;

@Getter
public class Account {
    private final byte[] publicKey;
    private final byte[] secretKey;

    public Account(byte[] privateKey) {
        if (privateKey.length != 64) throw new IllegalArgumentException("Private key must be 64 bytes");

        byte[] secretKey = new byte[32];
        System.arraycopy(privateKey, 0, secretKey, 0, 32);
        this.secretKey = secretKey;

        byte[] pubKey = new byte[32];
        System.arraycopy(privateKey, 32, pubKey, 0, 32);
        this.publicKey = pubKey;
    }

    public Account(byte[] secretKey, byte[] publicKey) {
        this(
                ByteBuffer.allocate(secretKey.length + publicKey.length)
                        .put(secretKey)
                        .put(publicKey)
                        .array()
        );
    }

    public static Account fromSecretKey(byte[] secretKey) {
        return KeyGen.fromSecretKey(secretKey);
    }

    public static Account fromPrivateKey(byte[] privateKey) {
        return new Account(privateKey);
    }

    public static Account generate() {
        return KeyGen.generate();
    }

    /**
     *
     * @return public key
     */
    public PublicKey getPublicKey() {
        return new PublicKey(publicKey);
    }

    /**
     *
     * @return secret 32-bytes secret key
     */
    public byte[] getSecretKey() {
        return Arrays.copyOfRange(secretKey, 0, 32);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Arrays.equals(publicKey, account.publicKey) && Objects.deepEquals(secretKey, account.secretKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(publicKey), Arrays.hashCode(secretKey));
    }
}
