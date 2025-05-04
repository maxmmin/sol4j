package io.github.maxmmin.sol.core.crypto;

import io.github.maxmmin.sol.core.crypto.exception.NonceNotFoundException;
import io.github.maxmmin.sol.core.crypto.exception.OnCurvePositionException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.i2p.crypto.eddsa.math.GroupElement;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PublicKeyUtils {
    public static int MAX_SEED_LENGTH = 32;
    public static String DERIVED_ADDRESS_FLAG = "ProgramDerivedAddress";

    public static PubkeyWithNonce findProgramAddress(byte[][] seeds, PublicKey programId) throws NonceNotFoundException {
        byte[][] seedsBuffer = new byte[seeds.length + 1][];
        System.arraycopy(seeds, 0, seedsBuffer, 0, seeds.length);
        for (int nonce = 255; nonce > 0; nonce--) {
            seedsBuffer[seedsBuffer.length - 1] = new byte[]{(byte) nonce};
            try {
                PublicKey address = createProgramAddress(seedsBuffer, programId);
                return new PubkeyWithNonce(address, (byte) nonce);
            } catch (OnCurvePositionException ignored) {}
        }
        throw new NonceNotFoundException();
    }

    public static PublicKey createProgramAddress(byte[][] seeds, PublicKey programId) throws OnCurvePositionException {
        byte[] data;

        try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            for (byte[] seed : seeds) {
                if (seed == null) throw new NullPointerException("Seed can't be null");
                if (seed.length > MAX_SEED_LENGTH) throw new IllegalArgumentException("Max seed length exceeded");
                buffer.write(seed);
            }

            byte[] programIdBytes = programId.getBytes();
            buffer.write(programIdBytes);

            byte[] derivedAddressFlagBytes = DERIVED_ADDRESS_FLAG.getBytes(StandardCharsets.UTF_8);
            buffer.write(derivedAddressFlagBytes);
            data = buffer.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        byte[] pubKeyBytes = getSHA256Digest().digest(data);
        if (isOnCurve(new PublicKey(pubKeyBytes))) throw new OnCurvePositionException("Invalid seeds, address must fall off the curve");
        else return new PublicKey(pubKeyBytes);
    }

    public static boolean isOnCurve(PublicKey publicKey) {
        try {
            GroupElement point = new GroupElement(EdDSANamedCurveSpecs.ED_25519.getCurve(), publicKey.getBytes(), false);
            return point.isOnCurve();
        } catch (IllegalArgumentException illegalArgumentException) {
            return false;
        }
    }

    private static MessageDigest getSHA256Digest() {
        try {
            return MessageDigest.getInstance("sha-256");
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new RuntimeException(noSuchAlgorithmException);
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class PubkeyWithNonce {
        private final PublicKey address;
        private final byte nonce;
    }
}
