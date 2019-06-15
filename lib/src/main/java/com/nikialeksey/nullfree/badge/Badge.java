package com.nikialeksey.nullfree.badge;

import com.nikialeksey.nullfree.NullfreeException;

import java.net.URL;

public interface Badge {

    void send(URL url) throws NullfreeException;

    void failIfRed() throws NullfreeException;
}
