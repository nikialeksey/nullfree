package com.nikialeksey.nullfree;

import org.takes.Response;
import org.takes.facets.fork.RqRegex;
import org.takes.facets.fork.TkRegex;
import org.takes.rq.RqMethod;
import org.takes.rq.form.RqFormBase;
import org.takes.rs.RsRedirect;
import org.takes.rs.RsWithBody;
import org.takes.rs.RsWithStatus;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class RepoInfo implements TkRegex {

    private final Map<String, String> store = new HashMap<>();

    @Override
    public Response act(final RqRegex req) throws IOException {
        final String user = req.matcher().group("user");
        final String repo = req.matcher().group("repo");
        final String path = user + "/" + repo;
        final String method = new RqMethod.Base(req).method();

        if ("POST".equals(method)) {
            store.put(path, new RqFormBase(req).param("badgeUrl").iterator().next());
            return new RsWithStatus(new RsWithBody("Saved!\n"), HttpURLConnection.HTTP_OK);
        } else if (store.containsKey(path)) {
            return new RsRedirect(store.get(path));
        } else {
            return new RsWithStatus(HttpURLConnection.HTTP_NOT_FOUND);
        }
    }
}
