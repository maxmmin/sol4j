package io.github.maxmmin.sol.core.crypto.transaction.sign;

import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveSpec;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
import net.i2p.crypto.eddsa.spec.EdDSAPrivateKeySpec;

import java.security.InvalidKeyException;
import java.security.Signature;
import java.security.SignatureException;

public class JavaEd25519Signer implements Signer {
    private final Signature signature = new EdDSAEngine();
    private final EdDSANamedCurveSpec ed25519 = EdDSANamedCurveTable.getByName("ed25519");

    @Override
    public byte[] sign(byte[] message, byte[] secret) throws InvalidKeyException {
        EdDSAPrivateKeySpec keySpec = new EdDSAPrivateKeySpec(secret, ed25519);
        EdDSAPrivateKey privateKey = new EdDSAPrivateKey(keySpec);
        signature.initSign(privateKey);
        try {
            signature.update(message);
            return signature.sign();
        } catch (SignatureException signatureException) {
            throw new RuntimeException("Unexpected signature exception", signatureException);
        }
    }

}
