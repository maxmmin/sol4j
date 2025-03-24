package org.mxmn.sol.core.type.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Encoding implements Param<String> {
    BASE64("base64"), BASE58("base58"), JSON("json"), JSON_PARSED("jsonParsed");

    private final String value;
}
