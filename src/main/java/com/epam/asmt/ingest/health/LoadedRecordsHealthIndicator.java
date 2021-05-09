package com.epam.asmt.ingest.health;

import com.epam.asmt.ingest.loader.RecordLoadStatusHolder;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class LoadedRecordsHealthIndicator implements HealthIndicator {
  private final RecordLoadStatusHolder loadStatusHolder;

  public LoadedRecordsHealthIndicator(RecordLoadStatusHolder loadStatusHolder) {
    this.loadStatusHolder = loadStatusHolder;
  }

  @Override
  public Health health() {
    if (loadStatusHolder.isLoaded()) {
      return Health
          .up()
          .build();
    }
    return Health
        .down()
        .build();
  }
}
