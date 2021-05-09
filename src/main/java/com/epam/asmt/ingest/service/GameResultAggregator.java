package com.epam.asmt.ingest.service;

import com.epam.asmt.ingest.model.GameResultAggregate;

public interface GameResultAggregator {
  GameResultAggregate aggregate();
}
