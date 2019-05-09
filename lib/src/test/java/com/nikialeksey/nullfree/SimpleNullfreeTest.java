package com.nikialeksey.nullfree;

import com.nikialeksey.nullfree.badge.ShieldsIoBadge;
import com.nikialeksey.nullfree.sources.SimpleSources;
import com.nikialeksey.nullfree.sources.java.JavaSourceFile;
import com.nikialeksey.nullfree.sources.java.JavaSourceFileFactory;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.Collections;

public class SimpleNullfreeTest {
    @Test
    public void currentProjectBadgeCheck() throws Exception {
        URL url = new SimpleNullfree(
            new SimpleSources(
                new File("./src/"),
                new JavaSourceFileFactory()
            ),
            ShieldsIoBadge::new
        ).badge().asUrl();
        Assert.assertThat(url.toString(), IsEqual.equalTo("https://img.shields.io/badge/nullfree-approved-green.svg"));
    }

    @Test
    public void failIfRedWhenRed() throws Exception {
        try {
            new SimpleNullfree(
                new SimpleSources(
                    Collections.singletonList(
                        new JavaSourceFile(
                            "package simple.pkg;\n",
                            "\n",
                            "class B {\n",
                            "  void a() {\n",
                            "    String foo = null;",
                            "  }\n",
                            "  static class A {\n",
                            "    void b() {\n",
                            "      new Runnable() {\n",
                            "        public void run() {\n",
                            "          String name = null;\n",
                            "        }\n",
                            "      };\n",
                            "    }\n",
                            "  }\n",
                            "}\n"
                        )
                    )
                ),
                ShieldsIoBadge::new
            ).badge().failIfRed();
            Assert.fail();
        } catch (NullfreeException e) {
            // green
        }
    }

    @Test
    public void failIfRedWhenGreen() throws Exception {
        try {
            new SimpleNullfree(
                new SimpleSources(
                    Collections.singletonList(
                        new JavaSourceFile(
                            "class B {\n",
                            "  void a() {\n",
                            "    String foo = \"bar\";",
                            "  }\n",
                            "}\n"
                        )
                    )
                ),
                ShieldsIoBadge::new
            ).badge().failIfRed();
            // green
        } catch (NullfreeException e) {
            Assert.fail();
        }
    }
}