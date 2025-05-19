package io.github.maxmmin.sol.core.client.request.registry;

import io.github.maxmmin.sol.core.TagsHolder;
import io.github.maxmmin.sol.core.client.RequestThrottler;
import io.github.maxmmin.sol.core.client.RpcClient;
import io.github.maxmmin.sol.core.client.TestRpcClientFactory;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.type.request.Encoding;
import io.github.maxmmin.sol.core.client.type.response.account.base.BaseEncAccount;
import io.github.maxmmin.sol.core.client.type.response.account.base.DefaultEncAccount;
import io.github.maxmmin.sol.core.client.type.response.account.jsonparsed.JsonParsedAccount;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag(TagsHolder.INTEGRATION)
public class GetAccountInfoTest {
    private final RpcClient rpcClient = TestRpcClientFactory.create();
    private final RequestThrottler throttler = RequestThrottler.create();

    protected GetAccountInfoRequest getAccountInfo() {
        return rpcClient.getAccountInfo("Es9vMFrzaCERmJfrF4H2FYD4KCoNkY11McCe8BenwNYB");
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
        throttler.throttle();
        DefaultEncAccount defaultEncAccount = getAccountInfo().send().getValue();
        assertNotNull(defaultEncAccount.getData());
    }

    @Test
    public void testGetAccountInfoBase58() throws RpcException {
        throttler.throttle();
        BaseEncAccount base58EncodedAccount = getAccountInfo().base58().getValue();
        assertEquals(base58EncodedAccount.getData().get(1), "base58");
        assertNotNull(base58EncodedAccount.getData().get(0));
    }

    @Test
    public void testGetAccountInfoBase64() throws RpcException {
        throttler.throttle();
        BaseEncAccount base64EncodedAccount = getAccountInfo().base64().getValue();
        assertEquals(base64EncodedAccount.getData().get(1), "base64");
        assertNotNull(base64EncodedAccount.getData().get(0));
    }

    @Test
    public void testGetAccountInfoJsonParsed() throws RpcException {
        throttler.throttle();
        JsonParsedAccount jsonParsedAccount = getAccountInfo().jsonParsed().getValue();
        assertNotNull(jsonParsedAccount.getData());
    }
}
