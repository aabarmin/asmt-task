package com.epam.asmt.ingest.config;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StorageConfiguration {
  @NotNull
  private StorageType type;
}
