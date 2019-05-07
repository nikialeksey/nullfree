package com.nikialeksey.nullfree.nulls;

import com.nikialeksey.nullfree.sources.java.JavaSourceFile;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;

public class ExcludeComparisionsTest {
    @Test
    public void asdasd() throws Exception {
        Assert.assertThat(
            new ExcludeComparisions(
                new JavaSourceFile(
                    "class A {\n",
                    "    void method() {\n",
                    "        String name = \"some name\";\n",
                    "        if (name == null) {}",
                    "    }\n",
                    "}\n"
                ).nulls()
            ).asList().isEmpty(),
            IsEqual.equalTo(true)
        );
    }
}