package io.github.maxmmin.sol.core.client.type.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Encoding implements Param<String> {
    NIL(null), BASE64("base64"), BASE58("base58"), JSON("json"), JSON_PARSED("jsonParsed"), BASE64_ZSTD("base64+zstd");

    private final String value;

    public boolean isNil() {
        return this == NIL;
    }
}
