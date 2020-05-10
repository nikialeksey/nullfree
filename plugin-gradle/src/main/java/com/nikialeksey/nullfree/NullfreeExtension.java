package com.nikialeksey.nullfree;

import java.util.Arrays;
import java.util.List;

public class NullfreeExtension {
    private final List<Boolean> skipComparisions;
    private final List<Integer> threshold;
    private final List<Boolean> offline;

    public NullfreeExtension() {
        this(
            Arrays.asList(false),
            Arrays.asList(0),
            Arrays.asList(false)
        );
    }

    public NullfreeExtension(
        final List<Boolean> skipComparisions,
        final List<Integer> threshold,
        final List<Boolean> offline
    ) {
        this.skipComparisions = skipComparisions;
        this.threshold = threshold;
        this.offline = offline;
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

    public boolean getOffline() {
        return offline.get(0);
    }

    public void setOffline(final boolean offline) {
        this.offline.set(0, offline);
    }
}
