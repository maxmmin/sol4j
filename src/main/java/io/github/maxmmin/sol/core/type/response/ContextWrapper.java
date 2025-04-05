package io.github.maxmmin.sol.core.type.response;

import lombok.Data;

@Data
public class ContextWrapper<R> {
    private Context context;
    private R value;
}
