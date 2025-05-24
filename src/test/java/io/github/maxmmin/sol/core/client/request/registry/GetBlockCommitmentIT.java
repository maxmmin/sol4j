package io.github.maxmmin.sol.core.client.request.registry;

import io.github.maxmmin.sol.core.client.ITClientContext;
import io.github.maxmmin.sol.core.client.RequestThrottler;
import io.github.maxmmin.sol.core.client.RpcClient;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.type.response.block.BlockCommitment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigInteger;

@ExtendWith(ITClientContext.class)
public class GetBlockCommitmentIT {
    @Test
    public void testGetBlockCommitment() throws RpcException {
        RequestThrottler throttler = ITClientContext.getRequestThrottler();
        RpcClient client = ITClientContext.getRpcClient();

        throttler.throttle();

        BigInteger blockHeight = client.getBlockHeight().send();
        Assertions.assertNotNull(blockHeight);

        throttler.throttle();
        BlockCommitment commitment = client.getBlockCommitment(blockHeight).send();
        Assertions.assertNotNull(commitment);
    }
}
