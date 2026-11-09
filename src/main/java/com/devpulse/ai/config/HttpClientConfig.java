package com.devpulse.ai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.time.Duration;

@Configuration
public class HttpClientConfig {

  @Bean
  public RestClient rssRestClient(RestClient.Builder builder) {

    HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();

    JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory(httpClient);

    requestFactory.setReadTimeout(Duration.ofSeconds(15));

    return builder
        .requestFactory(requestFactory)
        .defaultHeader("User-Agent", "DevPulseAI/1.0")
        .build();
  }
}
