package com.nikialeksey.nullfree;

import com.nikialeksey.goo.Goo;
import com.nikialeksey.goo.GooException;
import com.nikialeksey.goo.Origin;
import com.nikialeksey.nullfree.badge.Badge;
import com.nikialeksey.nullfree.badge.ShieldsIoBadge;
import com.nikialeksey.nullfree.nulls.ExcludeComparisions;
import com.nikialeksey.nullfree.nulls.ExcludeSuppressed;
import com.nikialeksey.nullfree.nulls.Nulls;
import com.nikialeksey.nullfree.sources.SimpleSources;
import com.nikialeksey.nullfree.sources.java.JavaSourceFileFactory;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

@Mojo(name = "nullfree", threadSafe = true)
public class NullfreeMojo extends AbstractMojo {

    @Parameter(readonly = true, defaultValue = "${project.basedir}")
    private File baseDir;
    @Parameter(readonly = true, defaultValue = "false")
    private boolean skipComparisions;

    @Override
    public void execute() throws MojoExecutionException {
        try {
            final Origin origin = new Goo(
                new File(baseDir, ".git")
            ).origin();
            final Badge badge = new SimpleNullfree(
                new SimpleSources(
                    baseDir,
                    new JavaSourceFileFactory()
                ),
                nulls -> {
                    final Nulls wrapped;
                    if (skipComparisions) {
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
            ).badge();
            badge.send(
                new URL(
                    String.format(
                        "https://iwillfailyou.com/nullfree/%s/%s",
                        origin.user(),
                        origin.repo()
                    )
                )
            );
            badge.failIfRed();
        } catch (final GooException e) {
            throw new MojoExecutionException("Can not get the origin from git repo.", e);
        } catch (final NullfreeException e) {
            throw new MojoExecutionException("Can not make the nullfree analysis.", e);
        } catch (MalformedURLException e) {
            throw new MojoExecutionException("Can not make the url for sending nullfree badge.", e);
        }
    }
}
