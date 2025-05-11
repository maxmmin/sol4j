package io.github.maxmmin.sol.core.client.type.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ContextWrapper<R> {
    private Context context;
    private R value;
}
