package io.github.maxmmin.sol.core.type.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Commitment implements Param<String> {
    PROCESSED("processed"), CONFIRMED("confirmed"), FINALIZED("finalized");

    private final String value;

}
