package com.devpulse.ai.service;

public class ArticleNotFoundException extends RuntimeException {

  public ArticleNotFoundException(String message) {
    super(message);
  }
}
