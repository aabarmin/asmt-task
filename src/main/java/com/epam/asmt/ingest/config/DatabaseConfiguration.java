package com.epam.asmt.ingest.config;

import com.epam.asmt.ingest.service.repository.jpa.GameResultJpaRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Import({
    DataSourceAutoConfiguration.class,
    FlywayAutoConfiguration.class
})
@EnableJpaRepositories(basePackageClasses = GameResultJpaRepository.class)
@ConditionalOnProperty(prefix = "app.storage", name = "type", havingValue = "db")
public class DatabaseConfiguration {
}
