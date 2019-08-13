package com.nikialeksey.nullfree.sources.java;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.Problem;
import com.github.javaparser.ast.CompilationUnit;
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
import java.util.Optional;

public class JavaSourceFile implements SourceFile {

    private final JavaParser parser;
    private final String descriptor;
    private final Function0<InputStream, NullfreeException> source;

    public JavaSourceFile(final String... lines) {
        this(ParserConfiguration.LanguageLevel.RAW, lines);
    }

    public JavaSourceFile(final ParserConfiguration.LanguageLevel level, final String... lines) {
        this(
            new JavaParser(new ParserConfiguration().setLanguageLevel(level)),
            lines
        );
    }

    public JavaSourceFile(final JavaParser parser, final String... lines) {
        this(
            parser,
            () -> {
                final StringBuilder source = new StringBuilder();
                for (final String line : lines) {
                    source.append(line);
                }
                return new ByteArrayInputStream(source.toString().getBytes());
            },
            String.join("", lines)
        );
    }

    public JavaSourceFile(final String source) {
        this(ParserConfiguration.LanguageLevel.RAW, source);
    }

    public JavaSourceFile(final ParserConfiguration.LanguageLevel level, final String source) {
        this(
            new JavaParser(new ParserConfiguration().setLanguageLevel(level)),
            source
        );
    }

    public JavaSourceFile(final JavaParser parser, final String source) {
        this(
            parser,
            () -> new ByteArrayInputStream(source.getBytes()),
            source
        );
    }

    public JavaSourceFile(final File file) {
        this(ParserConfiguration.LanguageLevel.RAW, file);
    }

    public JavaSourceFile(final ParserConfiguration.LanguageLevel level, final File file) {
        this(
            new JavaParser(new ParserConfiguration().setLanguageLevel(level)),
            file
        );
    }

    public JavaSourceFile(final JavaParser parser, final File file) {
        this(
            parser,
            () -> {
                try {
                    return new FileInputStream(file);
                } catch (final IOException e) {
                    throw new NullfreeException(
                        String.format(
                            "Can not get an input stream from the file: %s",
                            file
                        ),
                        e
                    );
                }
            },
            file.getAbsolutePath()
        );
    }

    public JavaSourceFile(
        final JavaParser parser,
        final Function0<InputStream, NullfreeException> source,
        final String descriptor
    ) {
        this.parser = parser;
        this.source = source;
        this.descriptor = descriptor;
    }

    @Override
    public Nulls nulls() throws NullfreeException {
        try (final InputStream stream = source.execute()) {
            final List<Null> result = new ArrayList<>();
            final ParseResult<CompilationUnit> parsed = parser.parse(stream);
            if (parsed.isSuccessful()) {
                final Optional<CompilationUnit> optionalUnit = parsed.getResult();
                if (optionalUnit.isPresent()) {
                    for (NullLiteralExpr nullLiteralExpr : optionalUnit.get().findAll(NullLiteralExpr.class)) {
                        result.add(new JavaNull(nullLiteralExpr));
                    }
                }
            } else {
                final StringBuilder problems = new StringBuilder();
                for (final Problem problem : parsed.getProblems()) {
                    problems.append(problem.toString());
                    problems.append("\n");
                }
                throw new NullfreeException(
                    String.format(
                        "Can not count nulls in: '%s'. \nPlease, fix java syntax errors: \n%s",
                        descriptor,
                        problems.toString()
                    )
                );
            }
            return new SimpleNulls(result);
        } catch (final IOException e) {
            throw new NullfreeException(
                String.format(
                    "Can not count nulls in: '%s'.", descriptor
                ),
                e
            );
        } catch (ParseProblemException e) {
            throw new NullfreeException(
                String.format(
                    "Can not count nulls in: '%s'. \nPlease, fix java syntax errors and try again.", descriptor
                ),
                e
            );
        }
    }
}
