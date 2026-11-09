package com.devpulse.ai.collector.client;

public class RssFeedFetchException extends RuntimeException {

  public RssFeedFetchException(String message) {
    super(message);
  }

  public RssFeedFetchException(String message, Throwable cause) {
    super(message, cause);
  }
}
