package com.epam.asmt.ingest.service.repository.jpa;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "GAME_RESULTS")
public class GameResultEntity {
  @Id
  @Column(name = "GAME_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "GAME_DATE")
  private LocalDate gameDate;

  @ManyToOne
  @JoinColumn(name = "TEAM_HOME_ID")
  private GameTeamEntity teamHome;

  @ManyToOne
  @JoinColumn(name = "TEAM_WINNER_ID")
  private GameTeamEntity teamWinner;

  @ManyToOne
  @JoinColumn(name = "TEAM_AWAY_ID")
  private GameTeamEntity teamAway;

  @Column(name = "SCORE_HOME")
  private long scoreHome;

  @Column(name = "SCORE_AWAY")
  private long scoreAway;

  @Column(name = "SCORE_WINNER")
  private long scoreWinner;

  @Column(name = "GAME_TOURNAMENT")
  private String tournament;

  @Column(name = "GAME_CITY")
  private String city;

  @Column(name = "GAME_COUNTRY")
  private String country;
}
