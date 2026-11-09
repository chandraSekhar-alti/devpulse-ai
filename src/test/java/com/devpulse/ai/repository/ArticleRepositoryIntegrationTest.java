package com.devpulse.ai.repository;

import com.devpulse.ai.entity.Article;
import com.devpulse.ai.entity.NewsSource;
import com.devpulse.ai.enums.SourceType;
import com.devpulse.ai.repository.ArticleRepository;
import com.devpulse.ai.repository.NewsSourceRepository;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ArticleRepositoryIntegrationTest {
//
//  @Container
//  static final PostgreSQLContainer<?> postgres =
//      new PostgreSQLContainer<>("postgres:17-alpine")
//          .withDatabaseName("devpulse_test")
//          .withUsername("test_user")
//          .withPassword("test_password");
//
//  @DynamicPropertySource
//  static void configureProperties(DynamicPropertyRegistry registry) {
//    registry.add("spring.datasource.url", postgres::getJdbcUrl);
//
//    registry.add("spring.datasource.username", postgres::getUsername);
//
//    registry.add("spring.datasource.password", postgres::getPassword);
//
//    registry.add("spring.datasource.driver-class-name", postgres::getDriverClassName);
//  }
//
//  @Autowired private ArticleRepository articleRepository;
//
//  @Autowired private NewsSourceRepository newsSourceRepository;
//
//  @Test
//  void shouldSaveArticleSuccessfully() {
//
//    // Arrange
//    NewsSource source =
//        new NewsSource(
//            "Spring Blog", SourceType.RSS, "https://spring.io", "https://spring.io/blog.atom");
//
//    NewsSource savedSource = newsSourceRepository.saveAndFlush(source);
//
//    Article article =
//        new Article(
//            savedSource,
//            "Spring AI Release",
//            "https://example.com/spring-ai",
//            "https://example.com/spring-ai",
//            Instant.now());
//
//    // Act
//    Article savedArticle = articleRepository.saveAndFlush(article);
//
//    // Assert
//    assertThat(savedArticle.getId()).isNotNull();
//
//    assertThat(savedArticle.getTitle()).isEqualTo("Spring AI Release");
//
//    assertThat(savedArticle.getProcessingStatus()).isNotNull();
//
//    assertThat(savedArticle.getCreatedAt()).isNotNull();
//
//    assertThat(savedArticle.getUpdatedAt()).isNotNull();
//
//    assertThat(savedArticle.getVersion()).isNotNull();
//
//    assertThat(savedArticle.getNewsSource().getId()).isEqualTo(savedSource.getId());
//  }
//
//  @Test
//  void shouldPreventDuplicateCanonicalUrls() {
//
//    // Arrange
//    NewsSource source =
//        new NewsSource(
//            "OpenJDK", SourceType.RSS, "https://openjdk.org", "https://openjdk.org/feed.xml");
//
//    NewsSource savedSource = newsSourceRepository.saveAndFlush(source);
//
//    Article firstArticle =
//        new Article(
//            savedSource,
//            "Java Update",
//            "https://example.com/java",
//            "https://example.com/java",
//            Instant.now());
//
//    Article secondArticle =
//        new Article(
//            savedSource,
//            "Java Update Duplicate",
//            "https://example.com/java?ref=duplicate",
//            "https://example.com/java",
//            Instant.now());
//
//    // Act
//    articleRepository.saveAndFlush(firstArticle);
//
//    // Assert
//    assertThrows(
//        DataIntegrityViolationException.class, () -> articleRepository.saveAndFlush(secondArticle));
//  }
//
//  @Test
//  void shouldFindArticleByCanonicalUrl() {
//
//    NewsSource source =
//        new NewsSource(
//            "Spring Blog", SourceType.RSS, "https://spring.io", "https://spring.io/blog.atom");
//
//    NewsSource savedSource = newsSourceRepository.saveAndFlush(source);
//
//    Article article =
//        new Article(
//            savedSource,
//            "Spring Boot Update",
//            "https://example.com/spring",
//            "https://example.com/spring",
//            Instant.now());
//
//    articleRepository.saveAndFlush(article);
//
//    var result = articleRepository.findByCanonicalUrl("https://example.com/spring");
//
//    assertThat(result).isPresent();
//
//    assertThat(result.get().getTitle()).isEqualTo("Spring Boot Update");
//  }
//
//  @Test
//  void shouldIncrementVersionWhenArticleIsUpdated() {
//
//    NewsSource source =
//        new NewsSource(
//            "Spring Blog", SourceType.RSS, "https://spring.io", "https://spring.io/blog.atom");
//
//    NewsSource savedSource = newsSourceRepository.saveAndFlush(source);
//
//    Article article =
//        new Article(
//            savedSource,
//            "Spring AI Update",
//            "https://example.com/ai",
//            "https://example.com/ai",
//            Instant.now());
//
//    Article savedArticle = articleRepository.saveAndFlush(article);
//
//    Long initialVersion = savedArticle.getVersion();
//
//    // Modify the entity
//    savedArticle.markProcessing();
//
//    Article updatedArticle = articleRepository.saveAndFlush(savedArticle);
//
//    assertThat(updatedArticle.getVersion()).isGreaterThan(initialVersion);
//  }
}
