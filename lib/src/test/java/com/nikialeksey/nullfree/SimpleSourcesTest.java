package com.nikialeksey.nullfree;

import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class SimpleSourcesTest {
    @Test
    public void findNullsInCurrentProject() throws NullfreeException {
        Assert.assertThat(
            new SimpleSources(new File("./src/"), new JavaSourceFileFactory()).nulls().size(),
            IsEqual.equalTo(0)
        );
    }
}