package com.epam.asmt.ingest.config;

import com.epam.asmt.ingest.service.GameResultRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfiguration {
  @Bean
  public MeterBinder gameResultsCountMetric(GameResultRepository repository) {
    return (registry) -> Gauge.builder("records.count", () -> repository.findAll().size())
        .description("Number of records loaded into the system")
        .baseUnit("Item")
        .register(registry);
  }
}
