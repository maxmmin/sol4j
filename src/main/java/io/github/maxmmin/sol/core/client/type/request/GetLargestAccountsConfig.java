package io.github.maxmmin.sol.core.client.type.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GetLargestAccountsConfig {
    private final Commitment commitment;
    private final Filter filter;

    public static GetLargestAccountsConfig empty() {
        return GetLargestAccountsConfig.builder().build();
    }

    @RequiredArgsConstructor
    public enum Filter implements Param<String> {
        CIRCULATING("circulating"), NON_CIRCULATING("nonCirculating");

        private final String value;

        @Override
        public String getValue() {
            return value;
        }
    }
}
