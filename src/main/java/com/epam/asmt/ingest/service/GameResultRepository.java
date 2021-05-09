package com.epam.asmt.ingest.service;

import com.epam.asmt.ingest.model.GameResult;
import java.util.Collection;

public interface GameResultRepository {
  boolean save(GameResult result);

  Collection<GameResult> findAll();
}
