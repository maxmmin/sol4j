package io.github.maxmmin.sol.core.crypto;

import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.spec.EdDSAPublicKeySpec;

public class PrivateKeyGen {
    public static byte[] fromSecretKey(byte[] secretKeyBytes) {
        if (secretKeyBytes.length != 32) throw new IllegalArgumentException("Secret key must be 32 bytes");
        EdDSAPublicKeySpec publicKeySpec = new EdDSAPublicKeySpec(secretKeyBytes, EdDSANamedCurveSpecs.ED_25519);
        return new EdDSAPublicKey(publicKeySpec).getEncoded();
    }
}
