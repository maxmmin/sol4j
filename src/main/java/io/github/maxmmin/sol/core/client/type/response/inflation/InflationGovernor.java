package io.github.maxmmin.sol.core.client.type.response.inflation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InflationGovernor {
    private Double foundation;
    private Double foundationTerm;
    private Double initial;
    private Double taper;
    private Double terminal;
}
