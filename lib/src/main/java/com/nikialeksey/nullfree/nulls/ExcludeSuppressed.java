package com.nikialeksey.nullfree.nulls;

import java.util.ArrayList;
import java.util.List;

public class ExcludeSuppressed implements Nulls {

    private final Nulls origin;

    public ExcludeSuppressed(final Nulls origin) {
        this.origin = origin;
    }

    @Override
    public List<Null> asList() {
        final List<Null> filtered = new ArrayList<>();
        for (final Null nil : origin.asList()) {
            if (!nil.isSuppressed()) {
                filtered.add(nil);
            }
        }
        return filtered;
    }
}
