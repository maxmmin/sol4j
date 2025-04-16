package io.github.maxmmin.sol.core.crypto.transaction.sign;

public interface MessageSigner {
    String sign(byte[] message, byte[] key);
}
