package com.epam.asmt.ingest.model;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GameResult {
  LocalDate date;
  String homeTeam;
  String awayTeam;
  long homeScore;
  long awayScore;
  String tournament;
  String city;
  String country;
}
