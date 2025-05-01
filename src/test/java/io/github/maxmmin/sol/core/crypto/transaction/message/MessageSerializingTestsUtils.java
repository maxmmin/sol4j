package io.github.maxmmin.sol.core.crypto.transaction.message;

public class MessageSerializingTestsUtils {
    public static int[] toUintArray(byte[] source) {
        int[] target = new int[source.length];
        for (int i = 0; i < source.length; i++) {
            // cut off filled '1' if source byte was negative
            target[i] = source[i] & 0xFF;
        }
        return target;
    }
}
