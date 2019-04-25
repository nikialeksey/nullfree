package com.nikialeksey.nullfree;

import org.gradle.api.GradleScriptException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class NullfreePlugin implements Plugin<Project> {
    @Override
    public void apply(final Project target) {
        target.task("nullfree").doLast(task ->
            {
                try {
                    final Origin origin = new Git(
                        new File(target.getRootDir(), ".git")
                    ).origin();
                    new SimpleNullfree(
                        new SimpleSources(
                            target.getRootDir(),
                            new JavaSourceFileFactory()
                        ),
                        ShieldsIoBadge::new
                    ).badge().send(
                        new URL(
                            String.format(
                                "https://iwillfailyou.com/nullfree/%s/%s",
                                origin.user(),
                                origin.repo()
                            )
                        )
                    );
                } catch (final NullfreeException e) {
                    throw new GradleScriptException("Can not make the nullfree analysis.", e);
                } catch (MalformedURLException e) {
                    throw new GradleScriptException("Can not make the url for sending nullfree badge.", e);
                }
            }
        );
    }
}