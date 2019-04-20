package com.nikialeksey.nullfree;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;

import java.io.IOException;

public class App implements Take {

    private final Take origin;

    public App() {
        this(
            new TkFork(
                new FkRegex("/", "Nullfree"),
                new FkRegex(
                    "/(?<user>[^/]+)/(?<repo>[^/]+)",
                    new RepoInfo()
                )
            )
        );
    }

    private App(final Take origin) {
        this.origin = origin;
    }

    @Override
    public Response act(final Request req) throws IOException {
        return origin.act(req);
    }
}
