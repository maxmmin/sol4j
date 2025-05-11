package io.github.maxmmin.sol.core.client.type.response.block;

import io.github.maxmmin.sol.core.client.type.request.BlockRange;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BlockProduction {
    private Map<String, List<BigInteger>> byIdentity;
    private BlockRange range;
}
