package com.nikialeksey.nullfree;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;
import org.takes.http.FtRemote;

import java.io.File;
import java.net.URI;
import java.net.URL;

public class AppTest {

    @Test
    public void checkSystemWithCurrentProject() throws Exception {
        new FtRemote(new App()).exec((final URI home) -> {
            try {
                final Origin origin = new Git(
                    new File(new File("../"), ".git")
                ).origin();
                new SimpleNullfree(
                    new SimpleSources(
                        new File("../"),
                        new JavaSourceFileFactory()
                    ),
                    ShieldsIoBadge::new
                ).badge().send(
                    new URL(
                        String.format(
                            "%s/%s/%s",
                            home.toString(),
                            origin.user(),
                            origin.repo()
                        )
                    )
                );

                final URL saved = new URL(home.toString() + "/" + origin.user() + "/" + origin.repo());
                Assert.assertThat(
                    new BasicResponseHandler().handleResponse(
                        HttpClients.createDefault().execute(
                            new HttpGet(saved.toURI())
                        )
                    ),
                    IsEqual.equalTo("https://img.shields.io/badge/nullfree-approved-green.svg")
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

}