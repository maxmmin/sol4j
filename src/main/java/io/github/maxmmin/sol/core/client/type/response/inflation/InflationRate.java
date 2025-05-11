package io.github.maxmmin.sol.core.client.type.response.inflation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InflationRate {
    private Double total;
    private Double validator;
    private Double foundation;
    private BigInteger epoch;
}
