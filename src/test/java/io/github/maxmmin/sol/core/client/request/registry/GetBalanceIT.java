package io.github.maxmmin.sol.core.client.request.registry;

import io.github.maxmmin.sol.core.client.ITClientContext;
import io.github.maxmmin.sol.core.client.ITClientConstants;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.type.response.ContextWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigInteger;

@ExtendWith(ITClientContext.class)
public class GetBalanceIT {
    @Test
    public void testGetBalance() throws RpcException {
        ITClientContext.getRequestThrottler().throttle();
        ContextWrapper<BigInteger> response = ITClientContext.getRpcClient().getBalance(ITClientConstants.ACCOUNT_PUBKEY).send();
        Assertions.assertNotNull(response);
    }
}
