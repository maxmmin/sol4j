package io.github.maxmmin.sol.core.client.type.response.block;

import io.github.maxmmin.sol.core.client.type.request.BlockRange;
import lombok.Data;

import java.math.BigInteger;
import java.util.Map;

@Data
public class BlockProduction {
    private Map<String, BigInteger[]> byIdentity;
    private BlockRange range;
}
