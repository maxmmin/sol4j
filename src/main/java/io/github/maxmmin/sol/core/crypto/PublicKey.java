package io.github.maxmmin.sol.core.crypto;

public class PublicKey {
    private final byte[] value;

    public PublicKey(byte[] publicKey) {
        this.value = publicKey;
    }

    public PublicKey(String publicKey) {
    }
}
