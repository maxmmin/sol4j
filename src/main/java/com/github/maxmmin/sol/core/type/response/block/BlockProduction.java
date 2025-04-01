package com.github.maxmmin.sol.core.type.response.block;

import com.github.maxmmin.sol.core.type.request.BlockRange;
import lombok.Data;

import java.math.BigInteger;
import java.util.Map;

@Data
public class BlockProduction {
    private Map<String, BigInteger[]> byIdentity;
    private BlockRange range;
}
