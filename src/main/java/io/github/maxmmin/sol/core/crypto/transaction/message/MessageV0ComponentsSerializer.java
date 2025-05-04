package io.github.maxmmin.sol.core.crypto.transaction.message;

import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.ShortU16;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class MessageV0ComponentsSerializer extends MessageComponentsSerializer {
    public byte[] serializeAddressLookupTables(List<MessageAddressTableLookup> tableLookups) {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            ShortU16 lookupsLength = ShortU16.valueOf(tableLookups.size());
            output.write(lookupsLength.getValue());

            for (MessageAddressTableLookup lookup : tableLookups) {
                PublicKey lookupTableKey = lookup.getAccountKey();
                output.write(lookupTableKey.getBytes());

                byte[] writableIndexes = lookup.getWritableIndexes();
                ShortU16 writableIndexesLength = ShortU16.valueOf(writableIndexes.length);
                output.write(writableIndexesLength.getValue());
                output.write(lookup.getWritableIndexes());

                byte[] readonlyIndexes = lookup.getReadonlyIndexes();
                ShortU16 readonlyIndexesLength = ShortU16.valueOf(readonlyIndexes.length);
                output.write(readonlyIndexesLength.getValue());
                output.write(lookup.getReadonlyIndexes());
            }

            return output.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
