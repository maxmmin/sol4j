package io.github.maxmmin.sol.core.crypto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Base58Test {
    @Test
    public void testDecoding() {
        String[] encodedStrings = {
                "", "1", "11", "1111111111", "11111xxxxxxxxxxx111xxxxx1111111", "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz",
                "1abcdefgABCDEFG", "3mJr7AoUXx2Wqd", "1BoatSLRHtKNngkdXEeobR76b53LETtpyT"
        };

        for (String encoded: encodedStrings) {
            byte[] bitcoinjBytes = org.bitcoinj.core.Base58.decode(encoded);
            byte[] ourBytes = io.github.maxmmin.sol.core.crypto.Base58.decodeFromString(encoded);
            assertArrayEquals(bitcoinjBytes, ourBytes);
        }
    }

    @Test
    public void testEncoding() {
        String[] strings = {
                new String(new byte[] {-21, -40}),
                "maklTt3v7nKEAxLKUHQEwpIDtY7DURpU", "TCIAfg0oCiKCm2kOuG0MHgJm4E6JYeV2", "RvwdQSFH8crXXUewrFJ33fJNozZNYhW7",
                "FQk3uKZeJtQLLnHIiW2lmEOz0PfL5kt8", "NXYcktDsfNuFjAj7MHR9OXOZ8cpWxjKo", "uw9lAWBn5iNWJwWqJyrESxVfazbZZKsF",
                "tfZknnkP42X8uihRIhnnRrh63r5qxMDg", "WyOj0qpoOZQvbEGLsmv7Sts1AqZadaLH", "PWWXUUeUrp5lULCncbYV1jmgHX8L9E6J",
                "AIpHy57EM8GglRFmPl1uv4y52rlpfjDv", "8bwpv7X95yO7u0QKiBcf7ehXVhkYipk7", "B52G0T7iEWJK7HY1yfIq6mKLuelLgXc3",
                "\0\0\0\0\0\0\0\0\0\0\0\0\0dsffsdfds\0", "fgh\0\054dsadsadsa\0__1211\\dsada", "", "\0"
        };

        for (String string: strings) {
            String bitcoinjBase58 = org.bitcoinj.core.Base58.encode(string.getBytes());
            String ourBase58 = io.github.maxmmin.sol.core.crypto.Base58.encodeToString(string.getBytes());
            assertEquals(bitcoinjBase58, ourBase58);
        }
    }
}
