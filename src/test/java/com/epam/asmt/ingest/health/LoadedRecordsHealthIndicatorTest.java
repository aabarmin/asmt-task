package com.epam.asmt.ingest.health;

import com.epam.asmt.ingest.loader.RecordLoadStatusHolder;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoadedRecordsHealthIndicatorTest {
  @Mock
  private RecordLoadStatusHolder holder;

  @InjectMocks
  private LoadedRecordsHealthIndicator uut;

  @MethodSource("params")
  @ParameterizedTest
  void health(boolean loaded, Status status) {
    when(holder.isLoaded()).thenReturn(loaded);

    final Health health = uut.health();

    assertEquals(status, health.getStatus());
  }

  public static Stream<Arguments> params() {
    return Stream.of(
        Arguments.of(true, Status.UP),
        Arguments.of(false, Status.DOWN)
    );
  }
}