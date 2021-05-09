package com.epam.asmt.ingest.service.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.cache.annotation.Cacheable;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Cacheable("aggregations")
public @interface CachesAggregationResult {
}
