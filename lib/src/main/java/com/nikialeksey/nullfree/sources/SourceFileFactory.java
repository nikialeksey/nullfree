package com.nikialeksey.nullfree.sources;

import java.nio.file.Path;
import java.nio.file.PathMatcher;

public interface SourceFileFactory {
    PathMatcher mask();

    SourceFile build(Path path);
}
