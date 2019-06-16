package com.nikialeksey.nullfree;

import java.util.Arrays;
import java.util.List;

public class NullfreeExtension {
    private final List<Boolean> skipComparisions;
    private final List<Integer> threshold;

    public NullfreeExtension() {
        this(Arrays.asList(false), Arrays.asList(0));
    }

    public NullfreeExtension(final List<Boolean> skipComparisions, final List<Integer> threshold) {
        this.skipComparisions = skipComparisions;
        this.threshold = threshold;
    }

    public boolean getSkipComparisions() {
        return skipComparisions.get(0);
    }

    public void setSkipComparisions(final boolean skipComparisions) {
        this.skipComparisions.set(0, skipComparisions);
    }

    public int getThreshold() {
        return threshold.get(0);
    }

    public int setThreshold(final int threshold) {
        return this.threshold.set(0, threshold);
    }
}
