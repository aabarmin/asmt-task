package com.epam.asmt.ingest.loader;

import com.epam.asmt.ingest.model.GameResult;
import com.epam.asmt.ingest.service.GameResultRepository;
import com.epam.asmt.ingest.service.GameResultService;
import com.epam.asmt.ingest.service.repository.InMemoryGameResultRepository;
import com.fasterxml.jackson.core.JsonFactory;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

class GameResultLoaderTest {
  private GameResultLoader uut;
  private GameResultRepository repository;

  private static WireMockServer WM_SERVER = new WireMockServer(options().dynamicPort());

  @BeforeAll
  static void beforeClass() {
    WM_SERVER.start();
    WireMock.configureFor(WM_SERVER.port());
  }

  @AfterAll
  static void afterClass() {
    WM_SERVER.stop();
  }

  @BeforeEach
  void setUp() {
    repository = new InMemoryGameResultRepository();
    uut = new GameResultLoader(
        new StreamGameResultParser(new JsonFactory()),
        new GameResultService(repository)
    );
    uut.init();
  }

  private byte[] contentFromResources(final String resourceName) throws IOException {
    final Path path = Paths.get("src", "test", "resources", resourceName);
    return Files.readAllBytes(path);
  }

  @Test
  void load_success() throws Exception {
    stubFor(
        get(urlEqualTo("/soccer/success.json"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(contentFromResources("soccer_success.json"))
            )
    );

    final boolean loaded = uut.load(URI.create(String.format(
        "http://localhost:%s/soccer/success.json",
        WM_SERVER.port()
    )));

    assertTrue(loaded, "Data wasn't loaded");

    final Collection<GameResult> results = repository.findAll();

    assertAll(
        () -> assertNotNull(results, "Results aren't available")
    );
  }

  @Test
  void load_failure() throws Exception {
    stubFor(
        get(urlEqualTo("/soccer/failure.json"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(contentFromResources("soccer_failure.json"))
            )
    );

    final boolean loaded = uut.load(URI.create(String.format(
        "http://localhost:%s/soccer/success.json",
        WM_SERVER.port()
    )));

    assertFalse(loaded, "Data was loaded but shouldn't");
  }
}