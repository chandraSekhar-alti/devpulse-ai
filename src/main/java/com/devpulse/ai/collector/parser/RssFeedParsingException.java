package com.devpulse.ai.collector.parser;

public class RssFeedParsingException extends RuntimeException {
  public RssFeedParsingException(String message) {
    super(message);
  }

  public RssFeedParsingException(String message, Throwable cause) {
    super(message, cause);
  }
}
