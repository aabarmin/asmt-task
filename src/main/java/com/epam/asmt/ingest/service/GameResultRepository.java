package com.epam.asmt.ingest.service;

import com.epam.asmt.ingest.model.GameResult;
import java.util.Collection;

/**
 * An abstraction on the top of storage of game results.
 */
public interface GameResultRepository {
  /**
   * Save a game result.
   * @param result to save.
   * @return {true} if the result was saved.
   */
  boolean save(GameResult result);

  /**
   * Find all game results. Use with caution.
   * @return a collection of game results.
   */
  Collection<GameResult> findAll();
}
