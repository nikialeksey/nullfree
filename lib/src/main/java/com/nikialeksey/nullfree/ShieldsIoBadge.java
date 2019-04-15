package com.nikialeksey.nullfree;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ShieldsIoBadge implements Badge {

    private final List<Null> nulls;

    public ShieldsIoBadge(List<Null> nulls) {
        this.nulls = nulls;
    }

    @Override
    public URL asUrl() throws NullfreeException {
        final int nullCount = nulls.size();
        final String message;
        if (nullCount == 0) {
            message = "approved";
        } else {
            message = "denied";
        }
        final String color;
        if (nullCount == 0) {
            color = "green";
        } else {
            color = "red";
        }
        try {
            return new URL(
                String.format(
                    "https://img.shields.io/badge/nullfree-%s-%s.svg",
                    message,
                    color
                )
            );
        } catch (MalformedURLException e) {
            throw new NullfreeException("Can not generate the URL for shields.io badge", e);
        }
    }
}
