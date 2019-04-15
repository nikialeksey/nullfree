package com.nikialeksey.nullfree;

import java.nio.file.Path;
import java.nio.file.PathMatcher;

public interface SourceFileFactory {
    PathMatcher mask();

    SourceFile build(Path path);
}
