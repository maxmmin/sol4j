package io.github.maxmmin.sol.core.client.request.registry;

import io.github.maxmmin.sol.core.client.ITClientConstants;
import io.github.maxmmin.sol.core.client.ITClientContext;
import io.github.maxmmin.sol.core.client.RequestThrottler;
import io.github.maxmmin.sol.core.client.RpcClient;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.type.request.GetSignaturesForAddressConfig;
import io.github.maxmmin.sol.core.client.type.response.signature.SignatureInformation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

@ExtendWith(ITClientContext.class)
public class GetSignaturesForAddressIT {
    @Test
    public void testGetSignaturesForAddress() throws RpcException {
        RequestThrottler throttler = ITClientContext.getRequestThrottler();
        RpcClient client = ITClientContext.getRpcClient();

        GetSignaturesForAddressConfig cfg = GetSignaturesForAddressConfig.builder().limit(100).build();
        throttler.throttle();
        List<SignatureInformation> signatures = client.getSignaturesForAddress(ITClientConstants.ACCOUNT_PUBKEY, cfg).send();
        Assertions.assertEquals(100, signatures.size());

        GetSignaturesForAddressConfig previousCfg = GetSignaturesForAddressConfig.builder()
                .limit(10)
                .before(signatures.get(98).getSignature())
                .build();
        throttler.throttle();
        List<SignatureInformation> prevSignatures = client.getSignaturesForAddress(ITClientConstants.ACCOUNT_PUBKEY, previousCfg).send();
        Assertions.assertEquals(10, prevSignatures.size());
        Assertions.assertEquals(signatures.get(99).getSignature(), prevSignatures.get(0).getSignature());

        GetSignaturesForAddressConfig untilCfg = GetSignaturesForAddressConfig.builder()
                .limit(100)
                .before(signatures.get(97).getSignature())
                .until(signatures.get(99).getSignature())
                .build();
        throttler.throttle();
        List<SignatureInformation> untilSignatures = client.getSignaturesForAddress(ITClientConstants.ACCOUNT_PUBKEY, untilCfg).send();
        Assertions.assertEquals(1, untilSignatures.size());
        Assertions.assertEquals(signatures.get(98).getSignature(), untilSignatures.get(0).getSignature());
    }
}
