package io.github.maxmmin.sol.core.crypto.transaction.sign;

import java.security.InvalidKeyException;

public interface MessageSigner {
    byte[] sign(byte[] message, byte[] key) throws InvalidKeyException;
}
