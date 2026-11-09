package com.devpulse.ai.repository;

import com.devpulse.ai.entity.NewsSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NewsSourceRepository extends JpaRepository<NewsSource, UUID> {

  List<NewsSource> findAllByActiveTrue();

  boolean existsByFeedUrl(String feedUrl);
}
