package com.epam.asmt.ingest.config;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Data;

@Data
public class PreloadConfiguration {
  @Min(1_000)
  @Max(60_000)
  private int initialDelay;

  @Min(1_000)
  @Max(60_000)
  private int refreshDelay;
}
