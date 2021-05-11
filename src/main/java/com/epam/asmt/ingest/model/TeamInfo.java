package com.epam.asmt.ingest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

/**
 * Information about game result for the aggregate.
 */
@Value
public class TeamInfo {
  String team;
  long amount;
}
