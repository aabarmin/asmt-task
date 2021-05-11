package com.epam.asmt.ingest.service.repository.jpa;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface GameTeamJpaRepository extends CrudRepository<GameTeamEntity, Long> {
  Optional<GameTeamEntity> findByName(String name);
}
