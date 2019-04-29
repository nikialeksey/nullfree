package com.nikialeksey.nullfree.nulls;

import com.nikialeksey.nullfree.sources.java.JavaSourceFile;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;

public class ExcludeSuppressedTest {
    @Test
    public void emptyNullsWhenSuppressed() throws Exception {
        Assert.assertThat(
            new ExcludeSuppressed(
                new JavaSourceFile(
                    "class A {\n",
                    "    void method() {\n",
                    "        @SuppressWarnings(\"nullfree\")\n",
                    "        String name = null;\n",
                    "    }\n",
                    "}\n"
                ).nulls()
            ).asList().isEmpty(),
            IsEqual.equalTo(true)
        );
    }
}