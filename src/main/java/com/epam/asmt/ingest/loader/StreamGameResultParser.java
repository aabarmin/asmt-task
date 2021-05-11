package com.epam.asmt.ingest.loader;

import com.epam.asmt.ingest.model.GameResult;
import com.epam.asmt.ingest.model.ServiceError;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Parses response from the external providers.
 */
@Slf4j
@Component
public class StreamGameResultParser {
  private final JsonFactory factory;

  public StreamGameResultParser(JsonFactory factory) {
    this.factory = factory;
  }

  public void parse(final InputStream contentStream,
                              final Consumer<GameResult> resultConsumer,
                              final Consumer<ServiceError> errorConsumer) {

    try (final JsonParser parser = factory.createParser(contentStream)) {
      final JsonToken firstToken = parser.nextToken();
      if (firstToken == JsonToken.START_ARRAY) {
        // parsing success response
        parseGameResult(parser, resultConsumer);
      } else if (firstToken == JsonToken.START_OBJECT) {
        // parsing error message
        parseError(parser, errorConsumer);
      }
    } catch (IOException e) {
      log.error("Can't read content from stream", e);
      throw new RuntimeException(e);
    }
  }

  private void parseGameResult(final JsonParser parser,
                               final Consumer<GameResult> resultConsumer) throws IOException {

    GameResult.GameResultBuilder builder = GameResult.builder();
    while (parser.currentToken() != null) {
      final JsonToken token = parser.currentToken();

      if (token == JsonToken.START_OBJECT) {
        builder = GameResult.builder();
      } else if (token == JsonToken.END_OBJECT) {
        resultConsumer.accept(builder.build());
      } else if (token == JsonToken.FIELD_NAME) {
        final String fieldName = parser.currentName();
        parser.nextToken();
        if (StringUtils.equalsIgnoreCase("date", fieldName)) {
          builder.date(LocalDate.parse(parser.getText()));
        } else if (StringUtils.equalsIgnoreCase("homeTeam", fieldName)) {
          builder.homeTeam(parser.getText());
        } else if (StringUtils.equalsIgnoreCase("awayTeam", fieldName)) {
          builder.awayTeam(parser.getText());
        } else if (StringUtils.equalsIgnoreCase("tournament", fieldName)) {
          builder.tournament(parser.getText());
        } else if (StringUtils.equalsIgnoreCase("city", fieldName)) {
          builder.city(parser.getText());
        } else if (StringUtils.equalsIgnoreCase("country", fieldName)) {
          builder.country(parser.getText());
        } else if (StringUtils.equalsIgnoreCase("homeScore", fieldName)) {
          builder.homeScore(parser.getIntValue());
        } else if (StringUtils.equalsIgnoreCase("awayScore", fieldName)) {
          builder.awayScore(parser.getIntValue());
        } else {
          throw new IllegalArgumentException("Unsupported filed " + fieldName);
        }
      }

      // moving forward
      parser.nextToken();
    }
  }

  private void parseError(final JsonParser parser,
                          final Consumer<ServiceError> errorConsumer) throws IOException {
    final ServiceError.ServiceErrorBuilder builder = ServiceError.builder();
    while (parser.currentToken() != null) {
      final JsonToken token = parser.currentToken();

      if (token == JsonToken.FIELD_NAME) {
        final String fieldName = parser.currentName();
        parser.nextToken();
        if (StringUtils.equalsIgnoreCase("errorCode", fieldName)) {
          builder.errorCode(parser.getIntValue());
        } else if (StringUtils.equalsIgnoreCase("errorMessage", fieldName)) {
          builder.errorMessage(parser.getText());
        } else {
          throw new IllegalArgumentException("Unsupported field " + fieldName);
        }
      } else if (token == JsonToken.END_OBJECT) {
        errorConsumer.accept(builder.build());
      }

      // moving forward
      parser.nextToken();
    }
  }
}
