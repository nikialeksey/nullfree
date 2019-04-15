package com.nikialeksey.nullfree;

import java.util.List;

public interface Sources {
    List<Null> nulls() throws NullfreeException;
}
