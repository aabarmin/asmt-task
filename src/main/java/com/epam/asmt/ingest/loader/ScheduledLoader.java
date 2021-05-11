package com.epam.asmt.ingest.loader;

import com.epam.asmt.ingest.config.AppConfiguration;
import com.epam.asmt.ingest.config.IngestSourcesConfiguration;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Periodically pulls updates from ingest sources.
 */
@Slf4j
@Component
public class ScheduledLoader {
  private final AppConfiguration configuration;
  private final GameResultLoader loader;
  private final RecordLoadStatusHolder statusHolder;

  public ScheduledLoader(AppConfiguration configuration,
                         GameResultLoader loader,
                         RecordLoadStatusHolder statusHolder) {
    this.configuration = configuration;
    this.loader = loader;
    this.statusHolder = statusHolder;
  }

  @Scheduled(
      initialDelayString = "${app.ingest.preload.initial-delay}",
      fixedDelayString = "${app.ingest.preload.refresh-delay}"
  )
  public void load() {
    log.info("Start loading of game results");
    for (URI source : configuration.getIngest().getSources()) {
      final boolean result = loader.load(source);
      statusHolder.loaded(result);
    }
    log.info("Game results loaded");
  }
}
