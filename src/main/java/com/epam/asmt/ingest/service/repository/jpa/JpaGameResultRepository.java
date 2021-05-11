package com.epam.asmt.ingest.service.repository.jpa;

import com.epam.asmt.ingest.model.GameResult;
import com.epam.asmt.ingest.service.GameResultRepository;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Jpa backed for the {@link GameResultRepository}. Enabled only if
 * "app.storage.type" is equals to "db".
 */
@Component
@ConditionalOnProperty(prefix = "app.storage", name = "type", havingValue = "db")
public class JpaGameResultRepository implements GameResultRepository {
  private final GameResultJpaRepository resultRepository;
  private final GameTeamJpaRepository teamRepository;

  public JpaGameResultRepository(GameResultJpaRepository resultRepository,
                                 GameTeamJpaRepository teamRepository) {
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
    resultRepository.save(GameResultEntity.builder()
        .gameDate(result.getDate())
        .teamHome(findTeam(result.getHomeTeam()))
        .teamAway(findTeam(result.getAwayTeam()))
        .teamWinner(findTeam(findWinner(result)))
        .scoreHome(result.getHomeScore())
        .scoreAway(result.getAwayScore())
        .scoreWinner(findWinnerScore(result))
        .tournament(result.getTournament())
        .city(result.getCity())
        .country(result.getCountry())
        .build());
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
