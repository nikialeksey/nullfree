package com.nikialeksey.nullfree;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
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
            message = "declined";
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

    @Override
    public void send(final URL url) throws NullfreeException {
        try (
            final CloseableHttpClient httpClient = HttpClients.createDefault()
        ) {
            final HttpPost saveBadgeInfo = new HttpPost(url.toURI());
            saveBadgeInfo.setEntity(
                new UrlEncodedFormEntity(
                    Collections.singletonList(
                        new BasicNameValuePair("badgeUrl", asUrl().toString())
                    )
                )
            );
            httpClient.execute(saveBadgeInfo).close();
        } catch (ClientProtocolException e) {
            throw new NullfreeException("Nullfree service error when sending badge info.", e);
        } catch (URISyntaxException | IOException e) {
            throw new NullfreeException("Can not send the badge request.", e);
        }
    }
}
