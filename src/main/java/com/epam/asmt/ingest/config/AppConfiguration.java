package com.epam.asmt.ingest.config;

import javax.validation.Valid;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = "app")
public class AppConfiguration {
  @Valid
  @NestedConfigurationProperty
  private IngestSourcesConfiguration ingest = new IngestSourcesConfiguration();

  @Valid
  @NestedConfigurationProperty
  private StorageConfiguration storage = new StorageConfiguration();
}
