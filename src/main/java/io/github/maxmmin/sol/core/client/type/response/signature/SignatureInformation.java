package io.github.maxmmin.sol.core.client.type.response.signature;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigInteger;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignatureInformation {
    @JsonProperty("signature")
    private String signature;

    @JsonProperty("slot")
    private BigInteger slot;

    @JsonProperty("err")
    private Object err;

    @JsonProperty("memo")
    private String memo;

    @JsonProperty("blockTime")
    private Integer blockTime;

    @JsonProperty("confirmationStatus")
    private String confirmationStatus;
}
