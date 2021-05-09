package com.epam.asmt.ingest.service.aggregator;

import com.epam.asmt.ingest.model.GameResultAggregate;
import com.epam.asmt.ingest.model.TeamInfo;
import com.epam.asmt.ingest.service.GameResultAggregator;
import com.epam.asmt.ingest.service.repository.jpa.GameResultJPARepository;
import java.util.Collection;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "app.storage", name = "type", havingValue = "db")
public class JpaGameResultAggregator implements GameResultAggregator {
  private final GameResultJPARepository repository;

  public JpaGameResultAggregator(GameResultJPARepository repository) {
    this.repository = repository;
  }

  @Override
  public GameResultAggregate aggregate() {
    return new GameResultAggregate(
        findFirst(repository.findWinners()),
        findFirst(repository.findMostScore()),
        findFirst(repository.findLessReceived())
    );
  }

  private TeamInfo findFirst(Collection<TeamInfo> items) {
    if (items.isEmpty()) {
      return null;
    }
    return items.iterator().next();
  }
}
