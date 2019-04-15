package com.nikialeksey.nullfree;

import java.util.List;

public interface SourceFile {
    List<Null> nulls() throws NullfreeException;
}
