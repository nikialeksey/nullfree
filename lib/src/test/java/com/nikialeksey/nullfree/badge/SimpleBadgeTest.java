package com.nikialeksey.nullfree.badge;

import com.nikialeksey.nullfree.NullfreeException;
import com.nikialeksey.nullfree.sources.java.JavaSourceFile;
import org.junit.Assert;
import org.junit.Test;

public class SimpleBadgeTest {
    @Test
    public void failThreshold() {
        try {
            new SimpleBadge(
                new JavaSourceFile(
                    "class A {",
                    "  String name = null;",
                    "  String value = null;",
                    "  void run() {",
                    "     String a = null;",
                    "  }",
                    "}"
                ).nulls(),
                2
            ).failIfRed();
            Assert.fail();
        } catch (NullfreeException e) {
            // green
        }
    }
}