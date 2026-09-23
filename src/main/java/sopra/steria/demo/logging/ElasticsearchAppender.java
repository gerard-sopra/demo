package sopra.steria.demo.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class ElasticsearchAppender extends AppenderBase<ILoggingEvent> {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private String elasticsearchUri;

    public void setElasticsearchUri(String elasticsearchUri) {
        this.elasticsearchUri = elasticsearchUri;
    }

    @Override
    protected void append(ILoggingEvent event) {

        try {
            Map<String, Object> document = new HashMap<>();

            document.put(
                    "@timestamp",
                    Instant.ofEpochMilli(event.getTimeStamp()).toString()
            );

            document.put("level", event.getLevel().toString());
            document.put("logger", event.getLoggerName());
            document.put("thread", event.getThreadName());
            document.put("message", event.getFormattedMessage());

            String json = objectMapper.writeValueAsString(document);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(
                            elasticsearchUri + "/application-logs/_doc"
                    ))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            httpClient.sendAsync(
                    request,
                    HttpResponse.BodyHandlers.discarding()
            );

        } catch (Exception e) {
            addError("Unable to send log to Elasticsearch", e);
        }
    }
}