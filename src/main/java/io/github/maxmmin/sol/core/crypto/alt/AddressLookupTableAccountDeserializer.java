package io.github.maxmmin.sol.core.crypto.alt;

import io.github.maxmmin.sol.core.crypto.BytesUtil;
import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.TypeIndex;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AddressLookupTableAccountDeserializer {
    public static int LOOKUP_TABLE_META_SIZE = 56;

    public AddressLookupTableState deserialize(byte[] data) {
        int typeIndex = getTypeIndex(data);
        if (typeIndex != TypeIndex.ADDRESS_LOOKUP_TABLE)
            throw new IllegalArgumentException("Type index does not match address lookup table");

        BigInteger deactivationSlot = getDeactivationSlot(data);
        BigInteger lastExtendedSlot = getLastExtendedSlot(data);
        int lastExtendedStartIndex = getLastExtendedStartIndex(data);
        PublicKey authority = getAuthority(data).orElse(null);
        List<PublicKey> addresses = getPublicKeys(data);

        return new AddressLookupTableState(deactivationSlot, lastExtendedSlot, lastExtendedStartIndex, authority, addresses);
    }

    protected int getTypeIndex(byte[] data) {
        if (data.length < 4)
            throw new IllegalArgumentException("Invalid account data length: cannot read type index");

        byte[] bytes = Arrays.copyOfRange(data, 0, 4);
        return ByteBuffer.wrap(bytes)
                .order(ByteOrder.LITTLE_ENDIAN)
                .getInt();
    }

    protected BigInteger getDeactivationSlot(byte[] data) {
        if (data.length < 12)
            throw new IllegalArgumentException("Invalid account data length: cannot read deactivation slot");

        byte[] slotBytes = Arrays.copyOfRange(data, 4, 12);
        return BytesUtil.readUint64LE(slotBytes);
    }

    protected BigInteger getLastExtendedSlot(byte[] data) {
        if (data.length < 20)
            throw new IllegalArgumentException("Invalid account data length: cannot read last extended slot");

        byte[] lastExtendedSlotBytes = Arrays.copyOfRange(data, 20, 28);
        return BytesUtil.readUint64LE(lastExtendedSlotBytes);
    }

    protected int getLastExtendedStartIndex(byte[] data) {
        if (data.length < 21)
            throw new IllegalArgumentException("Invalid account data length: cannot read last extended start index");

        byte lastExtendedSlotByte = data[20];
        return Byte.toUnsignedInt(lastExtendedSlotByte);
    }

    protected Optional<PublicKey> getAuthority(byte[] data) {
        if (data.length < 22)
            throw new IllegalArgumentException("Invalid account data length: cannot read authority");

        boolean hasAuthority = data[21] != 0;
        PublicKey publicKey;
        if (hasAuthority) {
            publicKey = new PublicKey(Arrays.copyOfRange(data, 22, 32));
        } else publicKey = null;
        return Optional.ofNullable(publicKey);
    }

    protected List<PublicKey> getPublicKeys(byte[] data) {
        int keysSize = data.length - LOOKUP_TABLE_META_SIZE;
        if (keysSize < 1 || keysSize % 32 != 0)
            throw new IllegalArgumentException("Invalid lookup table: cannot read keys");
        int keysCount = keysSize / 32;

        PublicKey[] publicKeys = new PublicKey[keysCount];
        int offset = LOOKUP_TABLE_META_SIZE;
        for (int i = 0; i < keysCount; i++) {
            PublicKey pubkey = new PublicKey(Arrays.copyOfRange(data, offset, offset + 32));
            publicKeys[i] = pubkey;
            offset += 32;
        }

        return List.of(publicKeys);
    }
}
