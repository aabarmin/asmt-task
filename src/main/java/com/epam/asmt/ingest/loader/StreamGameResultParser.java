package com.epam.asmt.ingest.loader;

import com.epam.asmt.ingest.model.GameResult;
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

@Slf4j
@Component
public class StreamGameResultParser {
  private JsonFactory factory = new JsonFactory();

  public void parse(InputStream stream, Consumer<GameResult> consumer) {
    try(final JsonParser parser = factory.createParser(stream)) {
      GameResult.GameResultBuilder builder = GameResult.builder();
      boolean shouldSave = true;
      while (parser.nextToken() != JsonToken.END_ARRAY) {
        final JsonToken token = parser.currentToken();
        if (token == JsonToken.START_OBJECT) {
          builder = GameResult.builder();
          shouldSave = true;
        } else if (token == JsonToken.END_OBJECT) {
          if (shouldSave) {
            consumer.accept(builder.build());
          }
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
          } else if (
              StringUtils.equalsIgnoreCase("errorCode", fieldName) ||
                  StringUtils.equalsIgnoreCase("errorMessage", fieldName)
          ) {
            shouldSave = false;
            log.warn("Service responded with error");
          } else {
            throw new IllegalArgumentException("Unsupported filed " + fieldName);
          }
        }
      }
    } catch (IOException e) {
      log.error("Can't parse JSON", e);
      throw new RuntimeException(e);
    }
  }
}
