package com.nikialeksey.nullfree;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.hamcrest.core.IsNull;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class NullfreePluginTest {
    @Test
    public void applyPlugin() {
        final Project project = ProjectBuilder.builder().withProjectDir(new File("../")).build();
        project.getPlugins().apply(NullfreePlugin.class);
        Assert.assertThat(project.getTasks().findByName("nullfree"), IsNull.notNullValue());
    }
}