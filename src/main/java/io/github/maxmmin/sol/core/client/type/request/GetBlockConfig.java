package io.github.maxmmin.sol.core.client.type.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GetBlockConfig {
    private final Commitment commitment;
    private final Encoding encoding;
    private final TransactionDetails transactionDetails;
    private final Integer maxSupportedTransactionVersion;
    private final Boolean rewards;

    public static GetBlockConfig empty() {
        return builder().build();
    }

    @RequiredArgsConstructor
    public enum TransactionDetails implements Param<String> {
        FULL("full"), ACCOUNTS("accounts"), SIGNATURES("signatures"), NONE("none");

        private final String value;

        @Override
        public String getValue() {
            return value;
        }
    }
}
