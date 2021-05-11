package com.epam.asmt.ingest.service;

import com.epam.asmt.ingest.model.GameResultAggregate;

/**
 * Aggregates leaderboard to one single position.
 */
public interface GameResultAggregator {
  /**
   * Aggregate the leaderboard.
   * @return a game results aggregate.
   */
  GameResultAggregate aggregate();
}
