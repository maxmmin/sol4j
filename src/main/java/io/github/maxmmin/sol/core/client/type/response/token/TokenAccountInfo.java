package io.github.maxmmin.sol.core.client.type.response.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TokenAccountInfo {
    private String address;
    private String amount;
    private Integer decimals;
    private @Nullable BigDecimal uiAmount;
    private String uiAmountString;
}
