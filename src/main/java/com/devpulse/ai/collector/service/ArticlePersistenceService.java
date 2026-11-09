package com.devpulse.ai.collector.service;

import com.devpulse.ai.entity.Article;
import com.devpulse.ai.entity.NewsSource;
import com.devpulse.ai.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticlePersistenceService {

  private final ArticleRepository articleRepository;

  public ArticlePersistenceService(ArticleRepository articleRepository) {
    this.articleRepository = articleRepository;
  }

  @Transactional
  public boolean saveIfNew(Article article) {

    if (articleRepository.existsByCanonicalUrl(article.getCanonicalUrl())) {
      return false;
    }

    articleRepository.save(article);
    return true;
  }
}
