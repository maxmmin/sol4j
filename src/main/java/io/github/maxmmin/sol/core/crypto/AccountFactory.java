package io.github.maxmmin.sol.core.crypto;

public class AccountFactory {
    public static Account fromSecretKey (byte[] secretKey) {
        byte[] publicKey = Key.fromSecretKey(secretKey);
        byte[] privateKey = new byte[64];
        System.arraycopy(secretKey, 0, privateKey, 0, secretKey.length);
        System.arraycopy(publicKey, 0, publicKey, 0, publicKey.length);
        return new Account(privateKey);
    }
}
