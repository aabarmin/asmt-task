package com.epam.asmt.ingest.loader;

import com.epam.asmt.ingest.service.GameResultService;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GameResultLoader {
  private final StreamGameResultParser resultParser;
  private final GameResultService resultService;

  private HttpClient httpClient;

  @PostConstruct
  public void init() {
    httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_1_1)
        .followRedirects(HttpClient.Redirect.ALWAYS)
        .build();
  }

  public boolean load(URI uri) {
    final HttpRequest request = HttpRequest.newBuilder(uri)
        .timeout(Duration.ofMinutes(1))
        .GET()
        .build();

    try {
      final HttpResponse<InputStream> httpResponse = httpClient.send(
          request,
          HttpResponse.BodyHandlers.ofInputStream()
      );
      if (httpResponse.statusCode() != 200) {
        log.warn("No response from {}", uri);
        return false;
      }

      resultParser.parse(httpResponse.body(), resultService::save, (serviceError) -> {
        log.error("Service responded with error code {} and message {}",
            serviceError.getErrorCode(), serviceError.getErrorMessage());
      });
    } catch (IOException e) {
      log.error("IOException during sending request to {}", uri, e);
      return false;
    } catch (InterruptedException e) {
      log.error("Interrupted Exception during sending request to {}", uri, e);
      return false;
    }

    return true;
  }
}
