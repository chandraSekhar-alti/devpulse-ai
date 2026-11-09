package com.devpulse.ai.controller;

import com.devpulse.ai.dto.response.ArticleDetailResponse;
import com.devpulse.ai.dto.response.ArticleResponse;
import com.devpulse.ai.enums.ProcessingStatus;
import com.devpulse.ai.service.ArticleNotFoundException;
import com.devpulse.ai.service.ArticleQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {

  private final ArticleQueryService articleQueryService;

  public ArticleController(ArticleQueryService articleQueryService) {
    this.articleQueryService = articleQueryService;
  }

  @GetMapping
  public ResponseEntity<Page<ArticleResponse>> getArticles(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size,
      @RequestParam(required = false) ProcessingStatus status,
      @RequestParam(required = false) String category,
      @RequestParam(required = false) String search) {

    if (page < 0 || size < 1 || size > 100) {
      return ResponseEntity.badRequest().build();
    }

    PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "publishedAt"));

    return ResponseEntity.ok(articleQueryService.getArticles(status, category, search, pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ArticleDetailResponse> getArticle(@PathVariable UUID id) {

    return ResponseEntity.ok(articleQueryService.getArticleById(id));
  }

  @ExceptionHandler(ArticleNotFoundException.class)
  public ResponseEntity<Void> handleArticleNotFound(ArticleNotFoundException exception) {

    return ResponseEntity.notFound().build();
  }
}
