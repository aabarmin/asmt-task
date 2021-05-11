package com.epam.asmt.ingest;

import com.epam.asmt.ingest.config.AppConfiguration;
import com.epam.asmt.ingest.config.IngestSourcesConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@EnableScheduling
@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class,
		FlywayAutoConfiguration.class
})
@EnableConfigurationProperties(AppConfiguration.class)
public class IngestAppSkeletonApplication {

	public static void main(String[] args) {
		SpringApplication.run(IngestAppSkeletonApplication.class, args);
	}

}
