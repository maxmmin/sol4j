package org.mxmn.sol.core.type.response;

import lombok.Data;

@Data
public class Context {
    private String apiVersion;
    private Long slot;
}
