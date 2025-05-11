package io.github.maxmmin.sol.core.crypto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {
    @Test
    public void testConstructors() {
        Account generated = Account.generate();

        Account fromPrivate = new Account(KeyGen.extractPrivateKey(generated));
        Account fromPair = new Account(generated.getSecretKey(), generated.getPublicKey().getBytes());

        assertEquals(generated, fromPrivate);
        assertEquals(fromPrivate, fromPair);
    }
}
