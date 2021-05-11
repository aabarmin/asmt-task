package com.epam.asmt.ingest.loader;

import com.epam.asmt.ingest.model.GameResult;
import com.epam.asmt.ingest.model.ServiceError;
import com.fasterxml.jackson.core.JsonFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StreamGameResultParserTest {
  @Spy
  private JsonFactory jsonFactory;

  @InjectMocks
  private StreamGameResultParser uut;

  @Mock
  private Consumer<GameResult> resultConsumer;

  @Mock
  private Consumer<ServiceError> errorConsumer;

  @Test
  void parse_errorResponse() {
    uut.parse(
        readContent("soccer_failure.json"),
        resultConsumer,
        errorConsumer
    );

    verify(resultConsumer, never()).accept(any(GameResult.class));
    verify(errorConsumer, atLeastOnce()).accept(any(ServiceError.class));
  }

  @Test
  void parse_successResponse() {
    uut.parse(
        readContent("soccer_success.json"),
        resultConsumer,
        errorConsumer
    );

    verify(resultConsumer, atLeastOnce()).accept(any(GameResult.class));
    verify(errorConsumer, never()).accept(any(ServiceError.class));
  }

  @SneakyThrows
  private InputStream readContent(String resource) {
    final Path path = Path.of("src", "test", "resources", resource);
    return new ByteArrayInputStream(Files.readAllBytes(path));
  }
}