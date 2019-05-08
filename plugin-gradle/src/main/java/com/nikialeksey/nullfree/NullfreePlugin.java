package com.nikialeksey.nullfree;

import com.nikialeksey.goo.Goo;
import com.nikialeksey.goo.GooException;
import com.nikialeksey.goo.Origin;
import com.nikialeksey.nullfree.badge.ShieldsIoBadge;
import com.nikialeksey.nullfree.nulls.ExcludeComparisions;
import com.nikialeksey.nullfree.nulls.ExcludeSuppressed;
import com.nikialeksey.nullfree.nulls.Nulls;
import com.nikialeksey.nullfree.sources.SimpleSources;
import com.nikialeksey.nullfree.sources.java.JavaSourceFileFactory;
import org.gradle.api.GradleScriptException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class NullfreePlugin implements Plugin<Project> {
    @Override
    public void apply(final Project target) {
        target.getExtensions().create("nullfree", NullfreeExtension.class);
        target.task("nullfree").doLast(task ->
            {
                final NullfreeExtension settings = target.getExtensions().getByType(NullfreeExtension.class);
                try {
                    final Origin origin = new Goo(
                        new File(target.getRootDir(), ".git")
                    ).origin();
                    new SimpleNullfree(
                        new SimpleSources(
                            target.getRootDir(),
                            new JavaSourceFileFactory()
                        ),
                        nulls -> {
                            final Nulls wrapped;
                            if (settings.getSkipComparisions()) {
                                wrapped = new ExcludeComparisions(
                                    new ExcludeSuppressed(nulls)
                                );
                            } else {
                                wrapped = new ExcludeSuppressed(nulls);
                            }
                            return new ShieldsIoBadge(
                                wrapped
                            );
                        }
                    ).badge().send(
                        new URL(
                            String.format(
                                "https://iwillfailyou.com/nullfree/%s/%s",
                                origin.user(),
                                origin.repo()
                            )
                        )
                    );
                } catch (final GooException e) {
                    throw new GradleScriptException("Can not get the origin from git repo.", e);
                } catch (final NullfreeException e) {
                    throw new GradleScriptException("Can not make the nullfree analysis.", e);
                } catch (MalformedURLException e) {
                    throw new GradleScriptException("Can not make the url for sending nullfree badge.", e);
                }
            }
        );
    }
}
