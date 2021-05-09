package com.epam.asmt.ingest.service.repository.jpa;

import com.epam.asmt.ingest.model.TeamInfo;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface GameResultJPARepository extends CrudRepository<GameResultEntity, Long> {
  @Query(
      "select gr from GameResultEntity gr " +
          "inner join GameTeamEntity home on home = gr.teamHome " +
          "inner join GameTeamEntity away on away = gr.teamAway " +
          "where gr.gameDate = :gameDate and " +
          "gr.tournament = :tournament and " +
          "gr.city = :city and " +
          "home.name = :teamHome and " +
          "away.name = :teamAway"
  )
  Optional<GameResultEntity> findResultEntity(
      @Param("gameDate") LocalDate gameDate,
      @Param("teamHome") String teamHome,
      @Param("teamAway") String teamAway,
      @Param("tournament") String tournament,
      @Param("city") String city
  );

  @Query(
      "select new com.epam.asmt.ingest.model.TeamInfo(team.name, count(gr.id)) " +
          "from GameResultEntity gr " +
          "inner join GameTeamEntity team on team = gr.teamWinner " +
          "group by team.name " +
          "order by count(gr.id) desc"
  )
  Collection<TeamInfo> findWinners();

  @Query(
      "select new com.epam.asmt.ingest.model.TeamInfo(team.name, gr.scoreWinner) " +
          "from GameResultEntity gr " +
          "inner join GameTeamEntity team on team = gr.teamWinner " +
          "order by gr.scoreWinner desc"
  )
  Collection<TeamInfo> findMostScore();

  @Query(
      value =
          "select new com.epam.asmt.ingest.model.TeamInfo(" +
          "case when (gr.scoreHome > gr.scoreAway) then away.name else home.name end, " +
          "case when (gr.scoreHome > gr.scoreAway) then gr.scoreAway else gr.scoreHome end) " +
          "from GameResultEntity gr " +
          "inner join GameTeamEntity home on home = gr.teamHome " +
          "inner join GameTeamEntity away on away = gr.teamAway " +
          "order by " +
              "case when (gr.scoreHome > gr.scoreAway) then gr.scoreAway else gr.scoreHome end asc"
  )
  Collection<TeamInfo> findLessReceived();
}
