package com.nikialeksey.nullfree.sources.java;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.nikialeksey.functions.Function0;
import com.nikialeksey.nullfree.NullfreeException;
import com.nikialeksey.nullfree.nulls.Null;
import com.nikialeksey.nullfree.nulls.Nulls;
import com.nikialeksey.nullfree.nulls.SimpleNulls;
import com.nikialeksey.nullfree.nulls.java.JavaNull;
import com.nikialeksey.nullfree.sources.SourceFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JavaSourceFile implements SourceFile {

    private final Function0<InputStream, NullfreeException> source;

    public JavaSourceFile(final String... lines) {
        this(() -> {
            final StringBuilder source = new StringBuilder();
            for (final String line : lines) {
                source.append(line);
            }
            return new ByteArrayInputStream(source.toString().getBytes());
        });
    }

    public JavaSourceFile(final String source) {
        this(() -> new ByteArrayInputStream(source.getBytes()));
    }

    public JavaSourceFile(final File file) {
        this(() -> {
            try {
                return new FileInputStream(file);
            } catch (IOException e) {
                throw new NullfreeException(
                    String.format(
                        "Can not get an input stream from the file: %s",
                        file
                    ),
                    e
                );
            }
        });
    }

    public JavaSourceFile(final Function0<InputStream, NullfreeException> source) {
        this.source = source;
    }

    @Override
    public Nulls nulls() throws NullfreeException {
        try (final InputStream stream = source.execute()) {
            final List<Null> result = new ArrayList<>();
            for (NullLiteralExpr nullLiteralExpr : StaticJavaParser.parse(stream).findAll(NullLiteralExpr.class)) {
                result.add(new JavaNull(nullLiteralExpr));
            }
            return new SimpleNulls(result);
        } catch (IOException e) {
            throw new NullfreeException("Can not count nulls.", e);
        }
    }
}
