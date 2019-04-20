package com.nikialeksey.nullfree;

import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class GitTest {
    @Test
    public void currentRepoOriginUrl() throws Exception {
        Assert.assertThat(
            new Git(new File("../.git")).origin().repo(),
            IsEqual.equalTo("nullfree")
        );
    }
}