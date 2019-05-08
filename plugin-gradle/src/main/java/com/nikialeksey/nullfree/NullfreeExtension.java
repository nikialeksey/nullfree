package com.nikialeksey.nullfree;

import java.util.Arrays;
import java.util.List;

public class NullfreeExtension {
    private final List<Boolean> skipComparisions;

    public NullfreeExtension() {
        this(Arrays.asList(false));
    }

    public NullfreeExtension(final List<Boolean> skipComparisions) {
        this.skipComparisions = skipComparisions;
    }

    public boolean getSkipComparisions() {
        return skipComparisions.get(0);
    }

    public void setSkipComparisions(final boolean skipComparisions) {
        this.skipComparisions.set(0, skipComparisions);
    }
}
