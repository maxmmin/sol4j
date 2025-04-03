package com.github.maxmmin.sol.core.type.response.token;

import lombok.Data;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;

@Data
public class TokenAccountInfo {
    private String address;
    private String amount;
    private Integer decimals;
    private @Nullable BigDecimal uiAmount;
    private String uiAmountString;
}
