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

public class PublicKeyFactory {
    public static int MAX_SEED_LENGTH = 32;
    public static String DERIVED_ADDRESS_FLAG = "ProgramDerivedAddress";

    private final MessageDigest sha256Digest;
    {
        try {
            sha256Digest = MessageDigest.getInstance("sha-256");
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new RuntimeException(noSuchAlgorithmException);
        }
    }

    public PubkeyWithNonce findProgramAddressSync(byte[][] seeds, PublicKey programId) throws NonceNotFoundException {
        byte[][] seedsBuffer = new byte[seeds.length + 1][];
        System.arraycopy(seeds, 0, seedsBuffer, 0, seeds.length);
        for (int nonce = 255; nonce > 0; nonce--) {
            seedsBuffer[seedsBuffer.length - 1] = new byte[]{(byte) nonce};
            try {
                PublicKey address = createProgramAddressSync(seedsBuffer, programId);
                return new PubkeyWithNonce(address, nonce);
            } catch (OnCurvePositionException ignored) {}
        }
        throw new NonceNotFoundException();
    }

    public PublicKey createProgramAddressSync(byte[][] seeds, PublicKey programId) throws OnCurvePositionException {
        byte[] data;

        try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            int offset;
            for (offset = 0; offset < seeds.length; offset++) {
                byte[] seed = seeds[offset];
                if (seed.length > MAX_SEED_LENGTH) throw new IllegalArgumentException("Max seed length exceeded");
                buffer.write(seed, offset, seed.length);
            }

            byte[] programIdBytes = programId.getBytes();
            buffer.write(programIdBytes, offset, programIdBytes.length);
            offset+=programIdBytes.length;

            byte[] derivedAddressFlagBytes = DERIVED_ADDRESS_FLAG.getBytes(StandardCharsets.UTF_8);
            buffer.write(derivedAddressFlagBytes, offset, derivedAddressFlagBytes.length);
            data = buffer.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        byte[] pubKeyBytes = sha256Digest.digest(data);
        if (isOnCurve(pubKeyBytes))
            throw new OnCurvePositionException("Invalid seeds, address must fall off the curve");
        else return new PublicKey(pubKeyBytes);
    }

    private static boolean isOnCurve(byte[] publicKeyBytes) {
        GroupElement point = new GroupElement(EdDSANamedCurveSpecs.ED_25519.getCurve(), publicKeyBytes);;
        return point.isOnCurve();
    }

    @Getter
    @RequiredArgsConstructor
    public static class PubkeyWithNonce {
        private final PublicKey address;
        private final int nonce;
    }
}
