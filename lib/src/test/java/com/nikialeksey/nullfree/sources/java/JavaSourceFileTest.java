package com.nikialeksey.nullfree.sources.java;

import com.nikialeksey.nullfree.NullfreeException;
import com.nikialeksey.nullfree.nulls.Null;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.StringContains;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

public class JavaSourceFileTest {

    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void oneNullLiteral() throws NullfreeException {
        Assert.assertThat(
            new JavaSourceFile(
                "class A {\n",
                "    private final String a = null;\n",
                "}\n"
            ).nulls().asList().size(),
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
            new JavaSourceFile(source).nulls().asList().size(),
            IsEqual.equalTo(1)
        );
    }

    @Test
    public void classSuppressedNull() throws Exception {
        final List<Null> nulls = new JavaSourceFile(
            "@SuppressWarnings(\"nullfree\")\n",
            "class A {\n",
            "    private final String a = null;\n",
            "}\n"
        ).nulls().asList();
        Assert.assertThat(
            nulls.size(),
            IsEqual.equalTo(1)
        );
        Assert.assertThat(
            nulls.get(0).isSuppressed(),
            IsEqual.equalTo(true)
        );
    }

    @Test
    public void methodSuppressedNull() throws Exception {
        final List<Null> nulls = new JavaSourceFile(
            "class A {\n",
            "    @SuppressWarnings(\"nullfree\")\n",
            "    void method() {\n",
            "        String name = null;\n",
            "    }\n",
            "}\n"
        ).nulls().asList();
        Assert.assertThat(
            nulls.size(),
            IsEqual.equalTo(1)
        );
        Assert.assertThat(
            nulls.get(0).isSuppressed(),
            IsEqual.equalTo(true)
        );
    }

    @Test
    public void assignSuppressedNull() throws Exception {
        final List<Null> nulls = new JavaSourceFile(
            "class A {\n",
            "    void method() {\n",
            "        @SuppressWarnings(\"nullfree\")\n",
            "        String name = null;\n",
            "    }\n",
            "}\n"
        ).nulls().asList();
        Assert.assertThat(
            nulls.size(),
            IsEqual.equalTo(1)
        );
        Assert.assertThat(
            nulls.get(0).isSuppressed(),
            IsEqual.equalTo(true)
        );
    }

    @Test
    public void fieldSuppressedNull() throws Exception {
        final List<Null> nulls = new JavaSourceFile(
            "class A {\n",
            "    @SuppressWarnings(\"nullfree\")\n",
            "    String name = null;\n",
            "}\n"
        ).nulls().asList();
        Assert.assertThat(
            nulls.size(),
            IsEqual.equalTo(1)
        );
        Assert.assertThat(
            nulls.get(0).isSuppressed(),
            IsEqual.equalTo(true)
        );
    }

    @Test
    public void fieldMultipleSuppressedNull() throws Exception {
        final List<Null> nulls = new JavaSourceFile(
            "class A {\n",
            "    @SuppressWarnings(value = {\"nullfree\", \"something_else\"})\n",
            "    String name = null;\n",
            "}\n"
        ).nulls().asList();
        Assert.assertThat(
            nulls.size(),
            IsEqual.equalTo(1)
        );
        Assert.assertThat(
            nulls.get(0).isSuppressed(),
            IsEqual.equalTo(true)
        );
    }

    @Test
    public void nullInComparision() throws Exception {
        final List<Null> nulls = new JavaSourceFile(
            "class A {\n",
            "    void a() {\n",
            "        String name = \"Some name\";",
            "        if (name != null) {}",
            "    }\n",
            "}\n"
        ).nulls().asList();
        Assert.assertThat(
            nulls.size(),
            IsEqual.equalTo(1)
        );
        Assert.assertThat(
            nulls.get(0).isInComparision(),
            IsEqual.equalTo(true)
        );
    }

    @Test
    public void nullNotInComparision() throws Exception {
        final List<Null> nulls = new JavaSourceFile(
            "class A {\n",
            "    void a() {\n",
            "        String name = \"Some name\";",
            "        if (name.equals(null)) {}",
            "    }\n",
            "}\n"
        ).nulls().asList();
        Assert.assertThat(
            nulls.size(),
            IsEqual.equalTo(1)
        );
        Assert.assertThat(
            nulls.get(0).isInComparision(),
            IsEqual.equalTo(false)
        );
    }

    @Test
    public void nullsWhenParsingError() throws Exception {
        try {
            new JavaSourceFile(
                "class A {\n",
                "    void int a() {\n",
                "    }\n",
                "}\n"
            ).nulls().asList();
        } catch (final NullfreeException e) {
            // green
        } catch (Throwable e) {
            Assert.fail();
        }
    }

    @Test
    public void nullsWhenParsingErrorInFile() throws Exception {
        final File source = folder.newFile("Main.java");
        try (final PrintWriter writer = new PrintWriter(source)) {
            writer.println("class A {");
            writer.println("    private public final String a = null;");
            writer.println("}");
        }
        try {
            new JavaSourceFile(source).nulls().asList();
        } catch (final NullfreeException e) {
            Assert.assertThat(e.getMessage(), StringContains.containsString("Main.java"));
        } catch (Throwable e) {
            Assert.fail();
        }
    }

    @Test
    public void nullsInPrivateInterfaceMethod() throws Exception {
        Assert.assertThat(
            new JavaSourceFile(
                "interface A {\n",
                "    private void a() {\n",
                "        String a = null;\n",
                "    }\n",
                "}\n"
            ).nulls().asList().size(),
            IsEqual.equalTo(1)
        );
    }

    @Test
    public void nullsInJava12SyntaxFile() throws Exception {
        Assert.assertThat(
            new JavaSourceFile(
                "public class Test {\n",
                "    enum Day {\n",
                "        MON, TUE, WED, THUR, FRI, SAT, SUN\n",
                "    };\n",
                "    @SuppressWarnings(\"preview\")\n",
                "    public String getDay_1 (Day today) {\n",
                "        String day = switch(today) {\n",
                "            case MON, TUE, WED, THUR, FRI -> \"Weekday\";\n",
                "            case SAT, SUN -> \"Weekend\";\n",
                "        };\n",
                "        return day;\n",
                "    }\n",
                "}"
            ).nulls().asList().size(),
            IsEqual.equalTo(0)
        );
    }

}