package com.nikialeksey.nullfree;

import java.net.URL;

public class Origin {

    private final URL url;

    public Origin(final URL url) {
        this.url = url;
    }

    public String user() {
        final String path = url.getPath();
        return path.substring(1, path.lastIndexOf('/'));
    }

    public String repo() {
        final String path = url.getPath();
        return path.substring(path.lastIndexOf('/') + 1, path.length() - 4);
    }
}
