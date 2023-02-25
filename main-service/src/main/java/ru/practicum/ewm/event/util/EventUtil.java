package ru.practicum.ewm.event.util;

import ru.practicum.ewm.client.Stats;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.model.RequestStatus;
import ru.practicum.ewm.request.repository.RequestRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EventUtil {

    public static void addViews(List<Event> events, StatsClient client) {
        Map<Long, Event> eventMap = events.stream().collect(Collectors.toMap(Event::getId, event -> event));
        List<Stats> views = client.getViewsAll(eventMap.keySet());
        views.forEach(h -> eventMap.get(Long.parseLong(h.getUri().split("/")[1])).setViews(h.getHits()));
    }

    public static void addConfirmedRequests(List<Event> events, RequestRepository requestRepository) {
        List<Long> eventsIds = events.stream().map(Event::getId).collect(Collectors.toList());
        List<Request> requests = requestRepository.findByEventInAndStatus(eventsIds, RequestStatus.CONFIRMED);
        events.forEach(event -> event.setConfirmedRequests(requests.stream()
                .filter(request -> request.getEvent().equals(event.getId())).count()));

    }
}
