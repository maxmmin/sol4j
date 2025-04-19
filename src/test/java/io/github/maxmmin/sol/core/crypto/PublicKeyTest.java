package io.github.maxmmin.sol.core.crypto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PublicKeyTest {
    @Test
    public void testValidKeys() {
        byte[][] keys = {
                {50, 28, -6, 98, -17, 68, -24, -113, 83, -84, 112, -70, 68, -12, -116, -14, 33, 25, -52, -23, -126, 65, 75, -99, -44, -26, -68, 115, -5, 38, 40, -1},
                {83, 35, 91, 72, -80, 36, 8, 88, -41, 21, 9, 19, -108, 13, -118, 117, -83, 34, 21, 84, 67, -73, -24, 102, 124, 74, 127, -96, 43, -103, 102, 94},
                {-75, -93, -5, -16, -62, -77, 106, -33, 94, 45, 127, -5, 113, -67, 114, 66, -27, 20, -21, 64, 97, -14, -44, -40, -32, -73, 23, 83, 107, 95, 104, -127},
                {95, 75, -60, 86, 25, 117, -14, -16, 108, 67, -39, 6, -101, -85, -110, -115, 93, 40, 83, 9, 88, 57, -35, 33, 38, 28, -69, 17, -125, -81, -31, -29},
                {-128, -8, 104, -15, -106, -44, -49, 3, -110, 21, -89, -115, 58, -14, 123, -87, 27, -54, -31, -107, -63, -91, 127, 105, -51, 64, 112, 62, 72, -79, -104, 86}
        };

        String[] base58Keys = {
                "4Nd1mYfTgS89mZRw6Y3oJqT68Nm7ZAKHVk2b9SgQGpXp",
                "6bY7Hq59vJZ3Pm1F3HL6RM1ceayjDCz8XEiL7SbU8P9o",
                "DE3pfCrmZ2eWdEtvAoUdLjEpSRzrRJCRvGM9T3C1UM1S",
                "7QzjvCXYNKMGMZpA7z4L52Z5FQn3ZYkZFH1FHZeeXqgn",
                "9gSt4Q5jTtZKqPKoDUfRpdsyCzFSjBaepgU98EgcWj3b"
        };

        for (int i = 0; i < keys.length; i++) {
            PublicKey fromRawBytes = new PublicKey(keys[i]);
            PublicKey fromBase = PublicKey.fromBase58(base58Keys[i]);
            Assertions.assertEquals(fromRawBytes, fromBase);
        }
    }
}
