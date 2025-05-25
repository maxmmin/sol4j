package io.github.maxmmin.sol.core.client.request.registry;

import io.github.maxmmin.sol.core.client.ITClientConstants;
import io.github.maxmmin.sol.core.client.ITClientContext;
import io.github.maxmmin.sol.core.client.RequestThrottler;
import io.github.maxmmin.sol.core.client.RpcClient;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.type.response.account.base.BaseEncProgramAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

@ExtendWith(ITClientContext.class)
public class GetProgramAccountsIT {
    @Test
    public void testGetProgramAccounts() throws RpcException {
        RequestThrottler throttler = ITClientContext.getRequestThrottler();
        RpcClient client = ITClientContext.getRpcClient();

        GetProgramAccountsRequest request = client.getProgramAccounts(ITClientConstants.ACCOUNT_PUBKEY);

        throttler.throttle();
        List<BaseEncProgramAccount> accounts58 = request.base58();
        Assertions.assertNotNull(accounts58);

        throttler.throttle();
        List<BaseEncProgramAccount> accounts64 = request.base64();
        Assertions.assertNotNull(accounts64);
    }
}
