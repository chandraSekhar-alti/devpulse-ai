package com.devpulse.ai.collector.client;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import java.net.URI;

@Component
public class RssFeedClient {

  private final RestClient restClient;

  public RssFeedClient(RestClient rssRestClient) {
    this.restClient = rssRestClient;
  }

  public byte[] fetch(String feedUrl) {

    URI uri = validateFeedUrl(feedUrl);

    try {
      byte[] response =
          restClient
              .get()
              .uri(uri)
              .accept(MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.ALL)
              .retrieve()
              .body(byte[].class);

      if (response == null || response.length == 0) {
        throw new RssFeedFetchException("RSS feed returned an empty response: " + uri);
      }

      return response;

    } catch (RestClientResponseException exception) {
      throw new RssFeedFetchException(
          "RSS feed returned HTTP status " + exception.getStatusCode(), exception);

    } catch (RestClientException exception) {
      throw new RssFeedFetchException("Unable to fetch RSS feed: " + uri, exception);
    }
  }

  private URI validateFeedUrl(String feedUrl) {

    try {
      URI uri = URI.create(feedUrl);

      String scheme = uri.getScheme();

      if (scheme == null
          || !(scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https"))
          || uri.getHost() == null
          || uri.getUserInfo() != null) {

        throw new IllegalArgumentException("Only valid HTTP/HTTPS feed URLs are allowed");
      }

      return uri;

    } catch (IllegalArgumentException exception) {
      throw new RssFeedFetchException("Invalid RSS feed URL: " + feedUrl, exception);
    }
  }
}
