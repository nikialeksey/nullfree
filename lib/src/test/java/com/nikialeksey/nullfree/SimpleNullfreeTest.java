package com.nikialeksey.nullfree;

import com.nikialeksey.nullfree.badge.ShieldsIoBadge;
import com.nikialeksey.nullfree.sources.SimpleSources;
import com.nikialeksey.nullfree.sources.java.JavaSourceFileFactory;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URL;

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
}