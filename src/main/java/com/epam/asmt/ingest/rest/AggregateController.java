package com.epam.asmt.ingest.rest;

import com.epam.asmt.ingest.model.GameResultAggregate;
import com.epam.asmt.ingest.service.GameResultAggregator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AggregateController {
  private final GameResultAggregator aggregator;

  public AggregateController(GameResultAggregator aggregator) {
    this.aggregator = aggregator;
  }

  @GetMapping("/aggregate")
  public GameResultAggregate aggregate() {
    return aggregator.aggregate();
  }
}
