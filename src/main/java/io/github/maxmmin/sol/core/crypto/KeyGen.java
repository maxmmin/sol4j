package io.github.maxmmin.sol.core.crypto;

import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.KeyPairGenerator;
import net.i2p.crypto.eddsa.spec.EdDSAPrivateKeySpec;

import java.security.KeyPair;
import java.security.KeyPairGeneratorSpi;

public class KeyGen {
    private final static KeyPairGeneratorSpi keyPairGenerator = new KeyPairGenerator();

    private static Account construct(KeyPair keyPair) {
        return construct((EdDSAPrivateKey) keyPair.getPrivate());
    }

    private static Account construct(EdDSAPrivateKey secretKey) {
        return new Account(secretKey.getSeed(), secretKey.getAbyte());
    }

    public static Account fromSecretKey(byte[] secretKeyBytes) {
        if (secretKeyBytes.length != 32) throw new IllegalArgumentException("Secret key must be 32 bytes");

        EdDSAPrivateKeySpec privateKeySpec = new EdDSAPrivateKeySpec(secretKeyBytes, EdDSANamedCurveSpecs.ED_25519);
        EdDSAPrivateKey privateKey = new EdDSAPrivateKey(privateKeySpec);

        return construct(privateKey);
    }

    public static Account generate() {
        return construct(keyPairGenerator.generateKeyPair());
    }
}
