package com.nikialeksey.nullfree;

import java.net.URL;

public interface Badge {
    URL asUrl() throws NullfreeException;

    void send(URL url) throws NullfreeException;
}
