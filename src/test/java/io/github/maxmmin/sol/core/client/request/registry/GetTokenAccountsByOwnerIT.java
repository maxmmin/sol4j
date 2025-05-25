package io.github.maxmmin.sol.core.client.request.registry;

import io.github.maxmmin.sol.core.client.ITClientConstants;
import io.github.maxmmin.sol.core.client.ITClientContext;
import io.github.maxmmin.sol.core.client.RequestThrottler;
import io.github.maxmmin.sol.core.client.RpcClient;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.type.request.GetTokenAccountsByOwnerParams;
import io.github.maxmmin.sol.core.client.type.response.ContextWrapper;
import io.github.maxmmin.sol.core.client.type.response.account.base.BaseEncProgramAccount;
import io.github.maxmmin.sol.core.client.type.response.account.jsonparsed.JsonParsedProgramAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

@ExtendWith(ITClientContext.class)
public class GetTokenAccountsByOwnerIT {
    @Test
    public void testGetTokenAccountsByOwnerAndTokenMint() throws RpcException {
        RequestThrottler throttler = ITClientContext.getRequestThrottler();
        RpcClient client = ITClientContext.getRpcClient();

        GetTokenAccountsByOwnerParams params = GetTokenAccountsByOwnerParams.byMint(ITClientConstants.ACCOUNT_PUBKEY);

        GetTokenAccountsByOwnerRequest request = client.getTokenAccountsByOwner(ITClientConstants.ACCOUNT_PUBKEY, params);

        throttler.throttle();
        ContextWrapper<List<BaseEncProgramAccount>> base64accounts = request.base64();
        Assertions.assertNotNull(base64accounts);
        if (!base64accounts.getValue().isEmpty()) Assertions.assertEquals("base64", base64accounts.getValue().get(0).getAccount().getData().get(1));

        throttler.throttle();
        ContextWrapper<List<JsonParsedProgramAccount>> parsedAccounts = request.jsonParsed();
        Assertions.assertNotNull(parsedAccounts);
    }

    @Test
    public void testGetTokenAccountsByOwnerAndTokenProgram() throws RpcException {
        RequestThrottler throttler = ITClientContext.getRequestThrottler();
        RpcClient client = ITClientContext.getRpcClient();

        GetTokenAccountsByOwnerParams params = GetTokenAccountsByOwnerParams.byProgramId(ITClientConstants.TOKEN_PROGRAM_PUBKEY);

        GetTokenAccountsByOwnerRequest request = client.getTokenAccountsByOwner(ITClientConstants.ACCOUNT_PUBKEY, params);

        throttler.throttle();
        ContextWrapper<List<BaseEncProgramAccount>> base64accounts = request.base64();
        Assertions.assertNotNull(base64accounts);
        if (!base64accounts.getValue().isEmpty()) Assertions.assertEquals("base64", base64accounts.getValue().get(0).getAccount().getData().get(1));

        throttler.throttle();
        ContextWrapper<List<JsonParsedProgramAccount>> parsedAccounts = request.jsonParsed();
        Assertions.assertNotNull(parsedAccounts);
    }
}
