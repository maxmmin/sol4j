package io.github.maxmmin.sol.core.crypto.transaction.sign;

import net.i2p.crypto.eddsa.EdDSAEngine;

import java.security.Signature;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class JavaEd25519MessageSigner implements MessageSigner {

    @Override
    public String sign(byte[] message, byte[] key) {

    }


}
