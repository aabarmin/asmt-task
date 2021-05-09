package com.epam.asmt.ingest.loader;

import com.epam.asmt.ingest.model.GameResult;
import com.epam.asmt.ingest.service.GameResultRepository;
import com.epam.asmt.ingest.service.GameResultService;
import com.epam.asmt.ingest.service.repository.InMemoryGameResultRepository;
import java.net.URI;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameResultLoaderTest {
  private GameResultLoader uut;
  private GameResultRepository repository;

  @BeforeEach
  void setUp() {
    repository = new InMemoryGameResultRepository();
    uut = new GameResultLoader(
        new StreamGameResultParser(),
        new GameResultService(repository)
    );
    uut.init();
  }

  @Test
  void load_checkLoadFromRemote() throws Exception {
    final boolean loaded = uut.load(URI.create("http://ec2-54-89-224-248.compute-1.amazonaws.com/soccer/2000"));

    assertTrue(loaded, "Data wasn't loaded");

    final Collection<GameResult> results = repository.findAll();

    assertAll(
        () -> assertNotNull(results, "Results aren't available")
    );
  }
}