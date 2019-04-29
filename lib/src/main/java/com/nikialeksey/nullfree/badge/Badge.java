package com.nikialeksey.nullfree.badge;

import com.nikialeksey.nullfree.NullfreeException;

import java.net.URL;

public interface Badge {
    URL asUrl() throws NullfreeException;

    void send(URL url) throws NullfreeException;
}
