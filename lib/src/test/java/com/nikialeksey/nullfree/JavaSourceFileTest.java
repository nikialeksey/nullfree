package com.nikialeksey.nullfree;

import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.PrintWriter;

public class JavaSourceFileTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void oneNullLiteral() throws NullfreeException {
        Assert.assertThat(
            new JavaSourceFile(
                "class A {\n" +
                    "    private final String a = null;\n" +
                    "}\n"
            ).nulls().size(),
            IsEqual.equalTo(1)
        );
    }

    @Test
    public void oneNullLiteralFromFile() throws Exception {
        final File source = folder.newFile();
        try (final PrintWriter writer = new PrintWriter(source)) {
            writer.println("class A {");
            writer.println("    private final String a = null;");
            writer.println("}");
        }
        Assert.assertThat(
            new JavaSourceFile(source).nulls().size(),
            IsEqual.equalTo(1)
        );
    }
}