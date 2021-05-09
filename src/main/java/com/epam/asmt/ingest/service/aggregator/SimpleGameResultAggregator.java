package com.epam.asmt.ingest.service.aggregator;

import com.epam.asmt.ingest.model.GameResultAggregate;
import com.epam.asmt.ingest.model.TeamInfo;
import com.epam.asmt.ingest.service.GameResultAggregator;
import com.epam.asmt.ingest.service.GameResultRepository;
import com.epam.asmt.ingest.service.cache.CachesAggregationResult;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "app.storage", name = "type", havingValue = "in_memory", matchIfMissing = true)
public class SimpleGameResultAggregator implements GameResultAggregator {
  private final GameResultRepository repository;

  public SimpleGameResultAggregator(GameResultRepository repository) {
    this.repository = repository;
  }

  @Override
  @CachesAggregationResult
  public GameResultAggregate aggregate() {
    final Optional<TeamInfo> optionalWinner = repository.findAll()
        .stream()
        .map(result -> result.getHomeScore() > result.getAwayScore() ?
            new TeamInfo(result.getHomeTeam(), result.getHomeScore()) :
            new TeamInfo(result.getAwayTeam(), result.getAwayScore()))
        .collect(Collectors.toMap(
            TeamInfo::getTeam,
            (a) -> 1,
            (a, b) -> a + b
        ))
        .entrySet()
        .stream()
        .max(Comparator.comparing(Map.Entry::getValue))
        .map(entry -> new TeamInfo(entry.getKey(), entry.getValue()));

    final Optional<TeamInfo> optionalMostScored = repository.findAll()
        .stream()
        .map(result -> result.getHomeScore() > result.getAwayScore() ?
            new TeamInfo(result.getHomeTeam(), result.getHomeScore()) :
            new TeamInfo(result.getAwayTeam(), result.getAwayScore()))
        .max(Comparator.comparing(TeamInfo::getAmount));

    final Optional<TeamInfo> optionalLessReceived = repository.findAll()
        .stream()
        .map(result -> result.getHomeScore() > result.getAwayScore() ?
            new TeamInfo(result.getHomeTeam(), result.getHomeScore()) :
            new TeamInfo(result.getAwayTeam(), result.getAwayScore()))
        .min(Comparator.comparing(TeamInfo::getAmount));

    if (optionalWinner.isEmpty() || optionalMostScored.isEmpty() || optionalLessReceived.isEmpty()) {
      throw new RuntimeException("No enough data");
    }

    return new GameResultAggregate(
        optionalWinner.get(),
        optionalMostScored.get(),
        optionalLessReceived.get()
    );
  }
}
