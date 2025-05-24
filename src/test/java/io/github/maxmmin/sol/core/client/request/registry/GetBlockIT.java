package io.github.maxmmin.sol.core.client.request.registry;

import io.github.maxmmin.sol.core.client.ITClientContext;
import io.github.maxmmin.sol.core.client.RequestThrottler;
import io.github.maxmmin.sol.core.client.RpcClient;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.type.request.GetBlockConfig;
import io.github.maxmmin.sol.core.client.type.request.GetBlockRequest;
import io.github.maxmmin.sol.core.client.type.response.block.BaseEncBlock;
import io.github.maxmmin.sol.core.client.type.response.block.JsonBlock;
import io.github.maxmmin.sol.core.client.type.response.block.JsonParsedBlock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigInteger;

@ExtendWith(ITClientContext.class)
public class GetBlockIT {
    @Test
    public void testGetBlockWithDifferentEncodings() throws RpcException {
        RequestThrottler requestThrottler = ITClientContext.getRequestThrottler();
        RpcClient client = ITClientContext.getRpcClient();

        requestThrottler.throttle();
        BigInteger lastSlotWithBlock = client.getBlockHeight().send();

        GetBlockConfig config = GetBlockConfig.builder().maxSupportedTransactionVersion(0).build();
        GetBlockRequest getBlockRequest = client.getBlock(lastSlotWithBlock, config);
        requestThrottler.throttle();


        requestThrottler.throttle();
        JsonBlock defaultBlock = getBlockRequest.send();
        Assertions.assertNotNull(defaultBlock);

        requestThrottler.throttle();
        BaseEncBlock base58Block = getBlockRequest.base58();
        Assertions.assertNotNull(base58Block);

        requestThrottler.throttle();
        BaseEncBlock base64Block = getBlockRequest.base64();
        Assertions.assertNotNull(base64Block);

        requestThrottler.throttle();
        JsonBlock jsonBlock = getBlockRequest.json();
        Assertions.assertNotNull(jsonBlock);

        requestThrottler.throttle();
        JsonParsedBlock jsonParsedBlock = getBlockRequest.jsonParsed();
        Assertions.assertNotNull(jsonParsedBlock);
    }
}
