package com.epam.asmt.ingest.model;

import lombok.Value;

@Value
public class GameResultAggregate {
  TeamInfo mostWin;
  TeamInfo mostScoredPerGame;
  TeamInfo lessReceivedPerGame;
}
