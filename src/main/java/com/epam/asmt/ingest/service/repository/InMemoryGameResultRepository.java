package com.epam.asmt.ingest.service.repository;

import com.epam.asmt.ingest.model.GameResult;
import com.epam.asmt.ingest.service.GameResultRepository;
import java.util.Collection;
import java.util.HashSet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * In-memory storage of game results. Enabled if "app.storage.type" has value "in_memory".
 */
@Component
@ConditionalOnProperty(prefix = "app.storage", name = "type", havingValue = "in_memory", matchIfMissing = true)
public class InMemoryGameResultRepository implements GameResultRepository {
  private Collection<GameResult> storage = new HashSet<>();

  @Override
  public boolean save(GameResult result) {
    return storage.add(result);
  }

  @Override
  public Collection<GameResult> findAll() {
    return storage;
  }
}
