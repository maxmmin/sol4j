package io.github.maxmmin.sol.core.client.type.response.inflation;

import lombok.Data;

@Data
public class InflationGovernor {
    private Double foundation;
    private Double foundationTerm;
    private Double initial;
    private Double taper;
    private Double terminal;
}
