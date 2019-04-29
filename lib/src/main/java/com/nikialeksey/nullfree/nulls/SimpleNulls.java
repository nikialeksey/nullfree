package com.nikialeksey.nullfree.nulls;

import java.util.List;

public class SimpleNulls implements Nulls {

    private final List<Null> nulls;

    public SimpleNulls(final List<Null> nulls) {
        this.nulls = nulls;
    }

    @Override
    public List<Null> asList() {
        return nulls;
    }
}
