package com.nikialeksey.nullfree;

import com.nikialeksey.functions.Function1;
import com.nikialeksey.nullfree.badge.Badge;
import com.nikialeksey.nullfree.nulls.Nulls;
import com.nikialeksey.nullfree.sources.Sources;

public class SimpleNullfree implements Nullfree {

    private final Sources sources;
    private final Function1<Badge, Nulls, NullfreeException> badgeFactory;

    public SimpleNullfree(
        final Sources sources,
        final Function1<Badge, Nulls, NullfreeException> badgeFactory
    ) {
        this.sources = sources;
        this.badgeFactory = badgeFactory;
    }

    @Override
    public Badge badge() throws NullfreeException {
        return badgeFactory.execute(sources.nulls());
    }
}
