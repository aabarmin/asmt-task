package com.epam.asmt.ingest.rest;

import com.epam.asmt.ingest.model.GameResultAggregate;
import com.epam.asmt.ingest.service.GameResultAggregator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AggregateController.class)
class AggregateControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private GameResultAggregator aggregator;

  @Test
  void aggregate_shouldCallAggregator() throws Exception {
    mockMvc.perform(get("/aggregate"))
        .andExpect(status().isOk());

    verify(aggregator, times(1)).aggregate();
  }
}