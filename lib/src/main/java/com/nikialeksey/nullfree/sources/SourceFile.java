package com.nikialeksey.nullfree.sources;

import com.nikialeksey.nullfree.NullfreeException;
import com.nikialeksey.nullfree.nulls.Nulls;

public interface SourceFile {
    Nulls nulls() throws NullfreeException;
}
