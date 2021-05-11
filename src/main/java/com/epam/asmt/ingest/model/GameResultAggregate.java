package com.epam.asmt.ingest.model;

import lombok.Value;

/**
 * Aggregate of the leaderboard.
 */
@Value
public class GameResultAggregate {
  TeamInfo mostWin;
  TeamInfo mostScoredPerGame;
  TeamInfo lessReceivedPerGame;
}
