package com.nikialeksey.nullfree;

import com.nikialeksey.functions.Function1;

import java.util.List;

public class SimpleNullfree implements Nullfree {

    private final Sources sources;
    private final Function1<Badge, List<Null>, NullfreeException> badgeFactory;

    public SimpleNullfree(
        final Sources sources,
        final Function1<Badge, List<Null>, NullfreeException> badgeFactory
    ) {
        this.sources = sources;
        this.badgeFactory = badgeFactory;
    }

    @Override
    public Badge badge() throws NullfreeException {
        return badgeFactory.execute(sources.nulls());
    }
}
