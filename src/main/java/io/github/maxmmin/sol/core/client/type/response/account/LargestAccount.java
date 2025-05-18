package io.github.maxmmin.sol.core.client.type.response.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LargestAccount {
    private String address;
    private BigInteger lamports;
}
