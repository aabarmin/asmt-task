package com.epam.asmt.ingest.service;

import com.epam.asmt.ingest.model.GameResult;
import com.epam.asmt.ingest.service.repository.InMemoryGameResultRepository;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameResultServiceTest {
  private GameResultService uut;
  private GameResultRepository repository;

  @BeforeEach
  void setUp() {
    repository = new InMemoryGameResultRepository();
    uut = new GameResultService(repository);
  }

  @Test
  void save_returnsTrueIfSaved() {
    final boolean saved = uut.save(createResult());

    assertTrue(saved, "Record should be saved");
  }

  @Test
  void save_returnsFalseIfNoChanges() {
    final boolean firstSave = uut.save(createResult());
    assertTrue(firstSave, "First save should return true");

    final boolean secondSave = uut.save(createResult());
    assertFalse(secondSave, "Second save shouldn't return true");
  }

  @Test
  void save_andThenReturn() {
    final Collection<GameResult> beforeSave = repository.findAll();

    assertAll(
        () -> assertNotNull(beforeSave),
        () -> assertTrue(beforeSave.isEmpty())
    );

    uut.save(createResult());

    final Collection<GameResult> afterSave = repository.findAll();

    assertAll(
        () -> assertNotNull(afterSave),
        () -> assertFalse(afterSave.isEmpty()),
        () -> assertEquals(1, afterSave.size())
    );
  }

  private GameResult createResult() {
    return GameResult.builder()
        .date(LocalDate.of(2001, Month.JANUARY, 25))
        .homeTeam("Benin")
        .awayTeam("Cameroon")
        .homeScore(1)
        .awayScore(3)
        .tournament("Friendly")
        .city("Cotonou")
        .country("Benin")
        .build();
  }
}