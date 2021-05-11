package com.epam.asmt.ingest.service;

import com.epam.asmt.ingest.model.GameResult;
import com.epam.asmt.ingest.service.cache.EvictAggregationResult;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * A service to deal with game results.
 */
@Service
public class GameResultService {
  private final GameResultRepository repository;

  public GameResultService(GameResultRepository repository) {
    this.repository = repository;
  }

  @EvictAggregationResult
  public boolean save(GameResult result) {
    return repository.save(result);
  }
}
