package org.mxmn.sol.extension.rotation;

public interface Prioritized {
    int DEFAULT_PRIORITY = 5;
    int MAX_PRIORITY = 10;
    int MIN_PRIORITY = 0;

    int getPriority();
}
