package com.nikialeksey.nullfree.sources;

import com.nikialeksey.nullfree.NullfreeException;
import com.nikialeksey.nullfree.sources.java.JavaSourceFileFactory;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class SimpleSourcesTest {
    @Test
    public void findNullsInCurrentProject() throws NullfreeException {
        Assert.assertThat(
            new SimpleSources(new File("./src/"), new JavaSourceFileFactory()).nulls().asList().size(),
            IsEqual.equalTo(0)
        );
    }
}