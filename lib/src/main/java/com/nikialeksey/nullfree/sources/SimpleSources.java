package com.nikialeksey.nullfree.sources;

import com.nikialeksey.nullfree.NullfreeException;
import com.nikialeksey.nullfree.nulls.Null;
import com.nikialeksey.nullfree.nulls.Nulls;
import com.nikialeksey.nullfree.nulls.SimpleNulls;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class SimpleSources implements Sources {

    private final File folder;
    private final SourceFileFactory sourceFactory;

    public SimpleSources(
        final File folder,
        final SourceFileFactory sourceFactory
    ) {
        this.folder = folder;
        this.sourceFactory = sourceFactory;
    }

    @Override
    public Nulls nulls() throws NullfreeException {
        final List<SourceFile> files = new ArrayList<>();
        try {
            Files.walkFileTree(folder.toPath(), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) {
                    if (sourceFactory.mask().matches(file)) {
                        files.add(sourceFactory.build(file));
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new NullfreeException(
                String.format(
                    "Can not count nulls in the %s",
                    folder
                ),
                e
            );
        }
        final List<Null> nulls = new ArrayList<>();
        for (SourceFile file : files) {
            nulls.addAll(file.nulls().asList());
        }
        return new SimpleNulls(nulls);
    }
}
