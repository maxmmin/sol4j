package io.github.maxmmin.sol.core.crypto.sign;

import java.security.InvalidKeyException;

public interface Signer {
    byte[] sign(byte[] message, byte[] key) throws InvalidKeyException;
}
