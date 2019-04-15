package com.nikialeksey.nullfree;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;

public class JavaSourceFileFactory implements SourceFileFactory {

    private final PathMatcher pathMatcher;

    public JavaSourceFileFactory() {
        this(FileSystems.getDefault().getPathMatcher("glob:**/*.java"));
    }

    private JavaSourceFileFactory(PathMatcher pathMatcher) {
        this.pathMatcher = pathMatcher;
    }

    @Override
    public PathMatcher mask() {
        return pathMatcher;
    }

    @Override
    public SourceFile build(Path path) {
        return new JavaSourceFile(path.toFile());
    }
}
