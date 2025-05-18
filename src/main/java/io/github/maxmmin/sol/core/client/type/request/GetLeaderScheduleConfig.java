package io.github.maxmmin.sol.core.client.type.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GetLeaderScheduleConfig {
    private final Commitment commitment;
    private final String identity;

    public static GetLeaderScheduleConfig empty() {
        return builder().build();
    }
}
