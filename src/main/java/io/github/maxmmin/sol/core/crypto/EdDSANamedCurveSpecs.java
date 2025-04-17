package io.github.maxmmin.sol.core.crypto;

import net.i2p.crypto.eddsa.spec.EdDSANamedCurveSpec;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;

public class EdDSANamedCurveSpecs {
    public static final EdDSANamedCurveSpec ED_25519 = EdDSANamedCurveTable.getByName("ed25519");
}
