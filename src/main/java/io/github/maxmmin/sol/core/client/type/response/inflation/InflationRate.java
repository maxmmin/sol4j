package io.github.maxmmin.sol.core.client.type.response.inflation;

import lombok.Data;

import java.math.BigInteger;

@Data
public class InflationRate {
    private Double total;
    private Double validator;
    private Double foundation;
    private BigInteger epoch;
}
