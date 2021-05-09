package com.epam.asmt.ingest.config;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

@Data
public class IngestSourcesConfiguration {
  @Valid
  @Size(min = 1, max = 1024)
  private Collection<URI> sources = List.of();

  @Valid
  @NestedConfigurationProperty
  private PreloadConfiguration preload = new PreloadConfiguration();
}
