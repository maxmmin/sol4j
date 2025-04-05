package io.github.maxmmin.sol.core.type.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetTokenAccountsByDelegateParams {
    private final String mint;
    private final String programId;
}
