package io.github.maxmmin.sol.core.crypto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class KeyGenTest {
    @Test
    public void testGeneration() {
        Account account = KeyGen.generate();

        byte[] secretKey = account.getSecretKey();

        Account accountFromSecretKey = KeyGen.fromSecretKey(secretKey);

        assertEquals(account, accountFromSecretKey);
    }
}
