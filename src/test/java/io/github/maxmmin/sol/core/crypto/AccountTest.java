package io.github.maxmmin.sol.core.crypto;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class AccountTest {
    @Test
    public void testConstructors() {
        Account generated = KeyGen.generate();

        Account fromPrivate = new Account(KeyGen.extractPrivateKey(generated));
        Account fromPair = new Account(generated.getSecretKey(), generated.getPublicKey().getBytes());

        assertEquals(generated, fromPrivate);
        assertEquals(fromPrivate, fromPair);
    }
}
