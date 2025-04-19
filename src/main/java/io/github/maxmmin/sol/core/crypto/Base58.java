package io.github.maxmmin.sol.core.crypto;

import java.math.BigInteger;
import java.util.*;

public class Base58 {
    private static Engine engine = new DefaultEngine();

    /**
     *
     * This method gives the user an opportunity to replace Base58 engine if he isn't satisfied with default one
     * @param engine - new engine to use
     */
    public static void setEngine(Engine engine) {
        Base58.engine = Objects.requireNonNull(engine, "Engine must not be null");
    }

    public static String encodeToString(byte[] raw) {
        return engine.encodeToString(raw);
    }

    public static byte[] encode(byte[] raw) {
        return engine.encode(raw);
    };

    public static byte[] decodeFromString(String encoded) {
        return engine.decodeFromString(encoded);
    }

    public static byte[] decode(byte[] encoded) {
        return engine.decode(encoded);
    }

    public interface Engine {
        String encodeToString(byte[] raw);
        byte[] encode(byte[] raw);

        byte[] decodeFromString(String encoded);
        byte[] decode(byte[] encoded);
    }

    public static class DefaultEngine implements Engine {
        private static final BigInteger k = BigInteger.valueOf(58);

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

        @Override
        public byte[] encode(byte[] input) {
            if (input.length == 0) return new byte[0];

            BigInteger base = new BigInteger(1, input);

            int counter = 0;
            List<Byte> remainders = new ArrayList<>();
            while (base.compareTo(BigInteger.ZERO) > 0) {
                byte remainder = base.mod(k).byteValue();
                remainders.add(counter++, remainder);
                base = base.divide(k);
            }

            int leadingZeros = getRawLeadingZerosCount(input);
            byte[] result = new byte[leadingZeros + counter];
            if (leadingZeros > 0) Arrays.fill(result, 0, leadingZeros, ENCODED_NULL_BYTE);
            for (int offset = leadingZeros; offset < result.length; offset++) {
                byte b = remainders.get(remainders.size() - (1 + offset - leadingZeros));
                result[offset] = (byte) ALPHABET[b];
            }
            return result;
        }

        @Override
        public String encodeToString(byte[] input) {
            return new String(encode(input));
        }

        private static int getRawLeadingZerosCount(byte[] rawInput) {
            int i;
            for (i = 0; i < rawInput.length; i++) {
                if (rawInput[i] != 0) break;
            }
            return i;
        }

        @Override
        public byte[] decode(byte[] input) throws IllegalArgumentException {
            if (input.length == 0) return new byte[0];

            int leadingZeros = getEncLeadingZerosCount(input);
            BigInteger value = BigInteger.ZERO;
            byte charByte;
            for (int i = leadingZeros; i < input.length; i++) {
                char c = (char) Byte.toUnsignedInt(input[i]);
                if (REVERSE_ALPHABET.containsKey(c)) charByte = REVERSE_ALPHABET.get(c);
                else throw new IllegalArgumentException("Invalid character: " + c);

                value = value.multiply(k).add(BigInteger.valueOf(charByte));
            }
            byte[] valueBytes = value.toByteArray();
            int valueSize = valueBytes[0] == 0 ? valueBytes.length - 1 : valueBytes.length;
            byte[] result = new byte[valueSize + leadingZeros];
            if (leadingZeros > 0) Arrays.fill(result, 0, leadingZeros, (byte) 0);
            System.arraycopy(valueBytes, valueBytes.length - valueSize, result, leadingZeros, valueSize);
            return result;
        }

        @Override
        public byte[] decodeFromString(String input) throws IllegalArgumentException {
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
}
