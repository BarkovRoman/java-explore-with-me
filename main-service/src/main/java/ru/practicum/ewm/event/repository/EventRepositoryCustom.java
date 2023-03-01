package ru.practicum.ewm.event.repository;

import ru.practicum.ewm.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepositoryCustom {

    List<Event> findEventByFilterDate(String text, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                      Boolean onlyAvailable, Integer from, Integer size, List<Long> categories);

    List<Event> findEventByFilterViews(String text, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                      Boolean onlyAvailable, List<Long> categories);
}
