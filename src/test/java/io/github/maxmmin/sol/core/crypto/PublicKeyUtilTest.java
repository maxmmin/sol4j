package io.github.maxmmin.sol.core.crypto;

import io.github.maxmmin.sol.core.crypto.exception.NonceNotFoundException;
import io.github.maxmmin.sol.core.crypto.exception.OnCurvePositionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PublicKeyUtilTest {
    @Test
    public void isOnCurveTest() {
        PublicKey onCurve = KeyGen.generate().getPublicKey();
        assertTrue(PublicKeyUtil.isOnCurve(onCurve));

        PublicKey offCurve = PublicKey.fromBase58("HtDPaF1e1FNgKn2bffMn8GTLByrSxNDcebMUpC9ZFfSd");
        assertFalse(PublicKeyUtil.isOnCurve(offCurve));
    }

    @Test
    public void findProgramAddressTest() throws NonceNotFoundException, OnCurvePositionException {
        PublicKey programId = PublicKey.fromBase58("BPFLoader1111111111111111111111111111111111");

        byte[] seed = "".getBytes();
        PublicKeyUtil.PubkeyWithNonce pubkey = PublicKeyUtil.findProgramAddress(new byte[][] {seed}, programId);
        PublicKey generated = PublicKeyUtil.createProgramAddress(new byte[][] {seed, {pubkey.getNonce()}}, programId);
        assertEquals(generated, pubkey.getAddress());
    }

    @Test
    public void findProgramAddressWithExpectedResultTest() throws NonceNotFoundException {
        PublicKey expectedKey = PublicKey.fromBase58("HsVL8JiWUwb4TE6SoEG5kY999p4nxtimv4bnDHX9ZxkE");
        byte expectedNonce = (byte) 249;

        PublicKey programId = PublicKey.fromBase58("BPFLoader1111111111111111111111111111111111");
        PublicKeyUtil.PubkeyWithNonce keyWithNonce = PublicKeyUtil.findProgramAddress(new byte[][]{"seed-48".getBytes()}, programId);

        assertEquals(expectedKey, keyWithNonce.getAddress());
        assertEquals(expectedNonce, keyWithNonce.getNonce());
    }

    @Test
    public void createProgramAddressTest() throws OnCurvePositionException {
        PublicKey programId = PublicKey.fromBase58("BPFLoader1111111111111111111111111111111111");
        PublicKey publicKey = PublicKey.fromBase58("SeedPubey1111111111111111111111111111111111");

        PublicKey programAddress;

        programAddress = PublicKeyUtil.createProgramAddress(
          new byte[][] {"".getBytes(), {1}}, programId
        );
        assertEquals(PublicKey.fromBase58("3gF2KMe9KiC6FNVBmfg9i267aMPvK37FewCip4eGBFcT"), programAddress);

        programAddress = PublicKeyUtil.createProgramAddress(
                new byte[][] {"☉".getBytes()}, programId
        );
        assertEquals(PublicKey.fromBase58("7ytmC1nT1xY4RfxCV2ZgyA7UakC93do5ZdyhdF3EtPj7"), programAddress);

        programAddress = PublicKeyUtil.createProgramAddress(
                new byte[][] {"Talking".getBytes(), "Squirrels".getBytes()}, programId
        );
        assertEquals(PublicKey.fromBase58("HwRVBufQ4haG5XSgpspwKtNd3PC9GM9m1196uJW36vds"), programAddress);

        programAddress = PublicKeyUtil.createProgramAddress(
                new byte[][] {publicKey.getBytes()}, programId
        );
        assertEquals(PublicKey.fromBase58("GUs5qLUfsEHkcMB9T38vjr18ypEhRuNWiePW2LoK4E3K"), programAddress);

        PublicKey programAddress2 = PublicKeyUtil.createProgramAddress(
                new byte[][] {"Talking".getBytes()}, programId
        );
        assertNotEquals(programAddress, programAddress2);

        assertThrows(IllegalArgumentException.class, () -> {
            PublicKeyUtil.createProgramAddress(
                    new byte[][] {new byte[PublicKeyUtil.MAX_SEED_LENGTH + 1]}, programId
            );
        });

    }
}
