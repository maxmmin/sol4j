package io.github.maxmmin.sol.core.client.type.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class GetTokenAccountsByOwnerParams {
    private final String mint;
    private final String programId;

    protected GetTokenAccountsByOwnerParams(String mint, String programId) {
        this.mint = mint;
        this.programId = programId;
    }

    public static GetTokenAccountsByOwnerParams byMint(String mint) {
        return new GetTokenAccountsByOwnerParams(mint, null);
    }

    public static GetTokenAccountsByOwnerParams byProgramId(String programId) {
        return new GetTokenAccountsByOwnerParams(null, programId);
    }
}
