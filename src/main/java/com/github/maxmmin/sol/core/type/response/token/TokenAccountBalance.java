package com.github.maxmmin.sol.core.type.response.token;

import lombok.Data;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;

@Data
public class TokenAccountBalance {
    private String amount;
    private Integer decimals;
    private @Deprecated @Nullable BigDecimal balance;
    private String uiAmountString;
}
