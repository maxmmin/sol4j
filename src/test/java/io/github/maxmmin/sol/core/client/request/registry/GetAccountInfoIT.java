package io.github.maxmmin.sol.core.client.request.registry;

import io.github.maxmmin.sol.core.client.ITClientContext;
import io.github.maxmmin.sol.core.client.ITClientConstants;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.type.request.Encoding;
import io.github.maxmmin.sol.core.client.type.response.account.base.BaseEncAccount;
import io.github.maxmmin.sol.core.client.type.response.account.base.DefaultEncAccount;
import io.github.maxmmin.sol.core.client.type.response.account.jsonparsed.JsonParsedAccount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ITClientContext.class)
public class GetAccountInfoIT {

    protected GetAccountInfoRequest getAccountInfo() {
        return ITClientContext.getRpcClient().getAccountInfo(ITClientConstants.ACCOUNT_PUBKEY);
    }

    @Test
    public void testGetAccountInfoSupportedEncodings() {
        GetAccountInfoRequest request = getAccountInfo();
        for (Encoding encoding : List.of(
                Encoding.BASE58, Encoding.BASE64, Encoding.BASE64_ZSTD, Encoding.JSON_PARSED
        )) {
            assertTrue(request.supportsEncoding(encoding));
        }
    }

    @Test
    public void testGetAccountInfoDefault() throws RpcException {
        ITClientContext.getRequestThrottler().throttle();
        DefaultEncAccount defaultEncAccount = getAccountInfo().send().getValue();
        assertNotNull(defaultEncAccount.getData());
    }

    @Test
    public void testGetAccountInfoBase58() throws RpcException {
        ITClientContext.getRequestThrottler().throttle();
        BaseEncAccount base58EncodedAccount = getAccountInfo().base58().getValue();
        assertEquals(base58EncodedAccount.getData().get(1), "base58");
        assertNotNull(base58EncodedAccount.getData().get(0));
    }

    @Test
    public void testGetAccountInfoBase64() throws RpcException {
        ITClientContext.getRequestThrottler().throttle();
        BaseEncAccount base64EncodedAccount = getAccountInfo().base64().getValue();
        assertEquals(base64EncodedAccount.getData().get(1), "base64");
        assertNotNull(base64EncodedAccount.getData().get(0));
    }

    @Test
    public void testGetAccountInfoJsonParsed() throws RpcException {
        ITClientContext.getRequestThrottler().throttle();
        JsonParsedAccount jsonParsedAccount = getAccountInfo().jsonParsed().getValue();
        assertNotNull(jsonParsedAccount.getData());
    }
}
