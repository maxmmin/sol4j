package io.github.maxmmin.sol.util;

import java.math.BigInteger;
import java.util.*;

public class Base58 {
    private static final int MULTIPLIER = 58;

    private static final char[] ALPHABET = {
            '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
            'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
            'j', 'k', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
            'y', 'z'
    };

    private static final Map<Character, Byte> REVERSE_ALPHABET;
    static {
        Map<Character, Byte> map = new HashMap<>();
        for (byte i = 0; i < ALPHABET.length; i++) {
            if (i < 0) throw new RuntimeException("Counter overflow");
            map.put(ALPHABET[i], i);
        }
        REVERSE_ALPHABET = Map.copyOf(map);
    }

    private static final byte ENCODED_NULL_BYTE = (byte) ALPHABET[0];

    public static byte[] encode(byte[] input) {
        if (input.length == 0) return new byte[0];

        BigInteger base = new BigInteger(input);
        BigInteger k = BigInteger.valueOf(MULTIPLIER);

        int counter = 0;
        List<Byte> remainders = new ArrayList<>();
        while (base.compareTo(BigInteger.ZERO) > 0) {
            byte remainder = base.mod(k).byteValueExact();
            remainders.add(counter++, remainder);
            base = base.divide(k);
        }

        int leadingZeros = getRawLeadingZerosCount(input);
        byte[] result = new byte[leadingZeros + counter];
        Arrays.fill(result, 0, leadingZeros, ENCODED_NULL_BYTE);
        for (int offset = leadingZeros; offset < result.length; offset++) {
            byte b = remainders.get(remainders.size() - (1 + offset - leadingZeros));
            result[offset] = (byte) ALPHABET[b];
        }
        return result;
    }

    public static String encodeToString(byte[] input) {
        return new String(encode(input));
    }

    private static int getRawLeadingZerosCount(byte[] rawInput) {
        int i;
        for (i = 0; i < rawInput.length; i++) {
            if (rawInput[i] != 0) break;
        }
        return i;
    }

    public static byte[] decode(byte[] input) throws IllegalArgumentException {
        if (input.length == 0) return new byte[0];

        int leadingZeros = getEncLeadingZerosCount(input);
        BigInteger value = BigInteger.ZERO;
        byte charByte;
        for (int i = input.length - 1; i >= leadingZeros; i--) {
            if (i == ENCODED_NULL_BYTE) continue;

            char c = (char) Byte.toUnsignedInt(input[i]);
            if (REVERSE_ALPHABET.containsKey(c)) charByte = REVERSE_ALPHABET.get(c);
            else throw new IllegalArgumentException("Invalid character: " + c);

            long sum = charByte * (long) Math.pow(MULTIPLIER, input.length - 1 - i);
            value = value.add(BigInteger.valueOf(sum));
        }
        byte[] valueBytes = value.toByteArray();
        byte[] result = new byte[valueBytes.length + leadingZeros];
        Arrays.fill(result, 0, leadingZeros, (byte) 0);
        System.arraycopy(valueBytes, 0, result, leadingZeros, valueBytes.length);
        return result;
    }

    public static byte[] decodeFromString(String input) throws IllegalArgumentException {
        return decode(input.getBytes());
    }

    private static int getEncLeadingZerosCount(byte[] encodedInput) {
        int i;
        for (i = 0; i < encodedInput.length; i++) {
            if (encodedInput[i] != ENCODED_NULL_BYTE) break;
        }
        return i;
    }
}
