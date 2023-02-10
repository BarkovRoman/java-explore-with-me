package ru.practicum.ewm.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class StatsClient extends BaseClient {
    @Autowired
    public StatsClient(@Value("${static-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build());
    }

    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
   // @Value("${static-server-app}")
   private static final String APP = "ewm-main-service";

    public void postStats(HttpServletRequest request) {
        NewHit newHit = new NewHit(APP, request.getRequestURI(), request.getRemoteAddr());
        post("/hit", newHit);
    }

    public Long getViews(Long eventId) {
        String url = "/stats?start={start}&end={end}&uris={uris}&unique={unique}";

        Map<String, Object> parameters = Map.of(
                "start", URLEncoder.encode(LocalDateTime.now()
                        .minusYears(100).format(format), StandardCharsets.UTF_8),
                "end", URLEncoder.encode(LocalDateTime.now().format(format), StandardCharsets.UTF_8),
                "uris", (List.of("/events/" + eventId)),
                "unique", "false"
        );
        ResponseEntity<Object> response = get(url, parameters);

        List<Stats> viewStatsList = response.hasBody() ? (List<Stats>) response.getBody() : List.of();
        return viewStatsList != null && !viewStatsList.isEmpty() ? viewStatsList.get(0).getHits() : 0L;
    }
}