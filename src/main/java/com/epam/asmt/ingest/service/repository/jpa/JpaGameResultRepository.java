package com.epam.asmt.ingest.service.repository.jpa;

import com.epam.asmt.ingest.model.GameResult;
import com.epam.asmt.ingest.service.GameResultRepository;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "app.storage", name = "type", havingValue = "db")
public class JpaGameResultRepository implements GameResultRepository {
  private final GameResultJPARepository resultRepository;
  private final GameTeamJdbcRepository teamRepository;

  public JpaGameResultRepository(GameResultJPARepository resultRepository,
                                 GameTeamJdbcRepository teamRepository) {
    this.resultRepository = resultRepository;
    this.teamRepository = teamRepository;
  }

  @Override
  public boolean save(GameResult result) {
    final Optional<GameResultEntity> foundOptional = resultRepository.findResultEntity(
        result.getDate(),
        result.getHomeTeam(),
        result.getAwayTeam(),
        result.getTournament(),
        result.getCity()
    );
    if (foundOptional.isPresent()) {
      return false;
    }
    resultRepository.save(new GameResultEntity(
        null,
        result.getDate(),
        findTeam(result.getHomeTeam()),
        findTeam(result.getAwayTeam()),
        findTeam(findWinner(result)),
        result.getHomeScore(),
        result.getAwayScore(),
        findWinnerScore(result),
        result.getTournament(),
        result.getCity(),
        result.getCountry()
    ));
    return true;
  }

  private long findWinnerScore(GameResult result) {
    return Long.max(result.getHomeScore(), result.getAwayScore());
  }

  private String findWinner(GameResult result) {
    return result.getHomeScore() > result.getAwayScore() ?
        result.getHomeTeam() : result.getAwayTeam();
  }

  private GameTeamEntity findTeam(final String teamName) {
    return teamRepository.findByName(teamName)
        .orElseGet(() -> teamRepository.save(new GameTeamEntity(null, teamName)));
  }

  @Override
  public Collection<GameResult> findAll() {
    return StreamSupport.stream(resultRepository.findAll().spliterator(), false)
        .map(entity -> GameResult.builder()
            .date(entity.getGameDate())
            .homeTeam(entity.getTeamHome().getName())
            .awayTeam(entity.getTeamAway().getName())
            .homeScore(entity.getScoreHome())
            .awayScore(entity.getScoreAway())
            .tournament(entity.getTournament())
            .city(entity.getCity())
            .country(entity.getCountry())
            .build())
        .collect(Collectors.toList());
  }
}
