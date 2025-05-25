package io.github.maxmmin.sol.core.client.request.registry;

import io.github.maxmmin.sol.core.client.ITClientConstants;
import io.github.maxmmin.sol.core.client.ITClientContext;
import io.github.maxmmin.sol.core.client.RequestThrottler;
import io.github.maxmmin.sol.core.client.RpcClient;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.type.response.ContextWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigInteger;

@ExtendWith(ITClientContext.class)
public class GetTokenAccountBalanceIT {
    @Test
    public void testGetTokenAccountBalance() throws RpcException {
        RequestThrottler throttler = ITClientContext.getRequestThrottler();
        RpcClient client = ITClientContext.getRpcClient();

        throttler.throttle();
        GetBalanceRequest request = client.getBalance(ITClientConstants.ACCOUNT_PUBKEY);
        ContextWrapper<BigInteger> balance = request.send();
        Assertions.assertNotNull(balance);
    }
}
