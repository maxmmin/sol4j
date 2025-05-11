package io.github.maxmmin.sol.core.client.type.response.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TokenAccountBalance {
    private String amount;
    private Integer decimals;
    private @Deprecated @Nullable BigDecimal balance;
    private String uiAmountString;
}
