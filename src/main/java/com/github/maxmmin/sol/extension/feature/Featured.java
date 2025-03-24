package com.github.maxmmin.sol.extension.feature;

import java.util.Collection;
import java.util.Set;

public interface Featured {
    boolean hasFeatures(Collection<Feature> features);
    Set<Feature> getFeatures();
}

