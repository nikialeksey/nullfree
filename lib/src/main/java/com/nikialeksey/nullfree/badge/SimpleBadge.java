package com.nikialeksey.nullfree.badge;

import com.nikialeksey.nullfree.NullfreeException;
import com.nikialeksey.nullfree.nulls.Null;
import com.nikialeksey.nullfree.nulls.Nulls;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SimpleBadge implements Badge {

    private final Nulls nulls;

    public SimpleBadge(final Nulls nulls) {
        this.nulls = nulls;
    }

    @Override
    public void send(final URL url) throws NullfreeException {
        try (
            final CloseableHttpClient httpClient = HttpClients.createDefault()
        ) {
            final List<NameValuePair> form = new ArrayList<>();
            for (final Null aNull : nulls.asList()) {
                form.add(new BasicNameValuePair("null", aNull.description()));
            }
            final HttpPost saveBadgeInfo = new HttpPost(url.toURI());
            saveBadgeInfo.setEntity(
                new UrlEncodedFormEntity(
                    form
                )
            );
            httpClient.execute(saveBadgeInfo).close();
        } catch (ClientProtocolException e) {
            throw new NullfreeException("Nullfree service error when sending badge info.", e);
        } catch (URISyntaxException | IOException e) {
            throw new NullfreeException("Can not send the badge request.", e);
        }
    }

    @Override
    public void failIfRed() throws NullfreeException {
        final List<Null> nulls = this.nulls.asList();
        if (!nulls.isEmpty()) {
            final StringBuilder message = new StringBuilder();
            for (final Null aNull : nulls) {
                message.append("Found:\n");
                message.append(aNull.description());
                message.append('\n');
            }
            throw new NullfreeException(
                String.format(
                    "Found nulls:\n%s",
                    message.toString()
                )
            );
        }
    }
}
