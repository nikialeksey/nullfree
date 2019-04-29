package com.nikialeksey.nullfree;

import com.nikialeksey.nullfree.badge.Badge;

public interface Nullfree {
    Badge badge() throws NullfreeException;
}
