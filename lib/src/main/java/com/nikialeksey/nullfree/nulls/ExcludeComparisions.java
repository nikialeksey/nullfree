package com.nikialeksey.nullfree.nulls;

import java.util.ArrayList;
import java.util.List;

public class ExcludeComparisions implements Nulls {

    private final Nulls origin;

    public ExcludeComparisions(final Nulls origin) {
        this.origin = origin;
    }

    @Override
    public List<Null> asList() {
        final List<Null> filtered = new ArrayList<>();
        for (final Null nil : origin.asList()) {
            if (!nil.isInComparision()) {
                filtered.add(nil);
            }
        }
        return filtered;
    }
}
