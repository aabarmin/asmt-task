package com.epam.asmt.ingest.loader;

import org.springframework.stereotype.Component;

/**
 * Holds status fo the records loading from external systems.
 */
@Component
public class RecordLoadStatusHolder {
  private boolean loaded = false;

  public boolean isLoaded() {
    return loaded;
  }

  public void loaded(boolean isLoaded) {
    this.loaded |= isLoaded;
  }
}
