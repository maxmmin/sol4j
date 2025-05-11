package io.github.maxmmin.sol.core.client.type.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Context {
    private String apiVersion;
    private Long slot;
}
