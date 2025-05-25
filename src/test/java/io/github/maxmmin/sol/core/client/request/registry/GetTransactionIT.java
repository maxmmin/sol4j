package io.github.maxmmin.sol.core.client.request.registry;

import io.github.maxmmin.sol.core.client.ITClientConstants;
import io.github.maxmmin.sol.core.client.ITClientContext;
import io.github.maxmmin.sol.core.client.RequestThrottler;
import io.github.maxmmin.sol.core.client.RpcClient;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.type.request.Commitment;
import io.github.maxmmin.sol.core.client.type.request.GetSignaturesForAddressConfig;
import io.github.maxmmin.sol.core.client.type.request.GetTransactionConfig;
import io.github.maxmmin.sol.core.client.type.response.tx.base.BaseEncConfirmedTransaction;
import io.github.maxmmin.sol.core.client.type.response.tx.json.JsonConfirmedTransaction;
import io.github.maxmmin.sol.core.client.type.response.tx.jsonparsed.JsonParsedConfirmedTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ITClientContext.class)
public class GetTransactionIT {
    @Test
    public void testGetTx() throws RpcException {
        RequestThrottler throttler = ITClientContext.getRequestThrottler();
        RpcClient client = ITClientContext.getRpcClient();

        GetSignaturesForAddressConfig signaturesCfg = GetSignaturesForAddressConfig.builder().limit(1).commitment(Commitment.FINALIZED).build();
        throttler.throttle();
        String signature = client.getSignaturesForAddress(ITClientConstants.ACCOUNT_PUBKEY, signaturesCfg).send().get(0).getSignature();

        GetTransactionConfig cfg = GetTransactionConfig.builder().commitment(Commitment.FINALIZED).maxSupportedTransactionVersion(0).build();
        GetTransactionRequest getTransactionRequest = client.getTransaction(signature, cfg);

        throttler.throttle();
        JsonConfirmedTransaction defaultTx = getTransactionRequest.send();
        Assertions.assertNotNull(defaultTx);

        throttler.throttle();
        BaseEncConfirmedTransaction base58Tx = getTransactionRequest.base58();
        Assertions.assertNotNull(base58Tx);
        Assertions.assertEquals(2, base58Tx.getTransaction().size());
        Assertions.assertEquals("base58", base58Tx.getTransaction().get(1));

        throttler.throttle();
        BaseEncConfirmedTransaction base64Tx = getTransactionRequest.base64();
        Assertions.assertNotNull(base64Tx);
        Assertions.assertEquals(2, base64Tx.getTransaction().size());
        Assertions.assertEquals("base64", base64Tx.getTransaction().get(1));

        throttler.throttle();
        JsonConfirmedTransaction jsonTx = getTransactionRequest.json();
        Assertions.assertNotNull(jsonTx);

        throttler.throttle();
        JsonParsedConfirmedTransaction jsonParsedTx = getTransactionRequest.jsonParsed();
        Assertions.assertNotNull(jsonParsedTx);
    }
}
