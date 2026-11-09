package com.devpulse.ai.collector.service;

public class CollectionRunNotFoundException extends RuntimeException {
  public CollectionRunNotFoundException(String message) {
    super(message);
  }
}
