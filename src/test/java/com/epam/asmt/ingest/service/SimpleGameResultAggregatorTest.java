package com.epam.asmt.ingest.service;

import com.epam.asmt.ingest.model.GameResult;
import com.epam.asmt.ingest.model.GameResultAggregate;
import com.epam.asmt.ingest.service.aggregator.SimpleGameResultAggregator;
import com.epam.asmt.ingest.service.repository.InMemoryGameResultRepository;
import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SimpleGameResultAggregatorTest {
  private GameResultAggregator uut;
  private GameResultRepository repository;

  @BeforeEach
  void setUp() {
    repository = new InMemoryGameResultRepository();
    uut = new SimpleGameResultAggregator(repository);
  }

  @Test
  void aggregate_shouldReturnValue() {
    Stream.of(
        create("Egypt", "Togo", 2, 1),
        create("Tunisia", "Togo", 7, 0),
        create("Trinidad and Tobago", "Canada", 0, 0),
        create("Burkina Faso", "Gabon", 1, 1),
        create("Egypt", "Canada", 6, 2)
    ).forEach(repository::save);

    final GameResultAggregate aggregation = uut.aggregate();

    assertAll(
        () -> assertNotNull(aggregation, "Aggregation snould not be null")
    );
  }

  private static GameResult create(final String homeTeam,
                                   final String awayTeam,
                                   final int homeScore,
                                   final int awayScore) {
    return GameResult.builder()
        .date(LocalDate.now())
        .homeTeam(homeTeam)
        .homeScore(homeScore)
        .awayTeam(awayTeam)
        .awayScore(awayScore)
        .tournament("Friendly")
        .city("Aswan")
        .country("Egypt")
        .build();
  }
}