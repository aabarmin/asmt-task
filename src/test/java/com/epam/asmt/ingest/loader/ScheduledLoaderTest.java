package com.epam.asmt.ingest.loader;

import com.epam.asmt.ingest.config.AppConfiguration;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScheduledLoaderTest {
  @Mock
  private GameResultLoader loader;

  @Spy
  private AppConfiguration configuration;

  @Spy
  private RecordLoadStatusHolder holder;

  @InjectMocks
  private ScheduledLoader uut;

  @BeforeEach
  void setUp() {
    configuration.getIngest().setSources(List.of(
        URI.create("http://abc.com"),
        URI.create("http://def.com")
    ));
  }

  @Test
  void load_shouldCheckEverySource() {
    when(loader.load(any(URI.class))).thenReturn(true);

    uut.load();

    verify(loader, times(2)).load(any(URI.class));
    assertTrue(holder.isLoaded());
  }
}