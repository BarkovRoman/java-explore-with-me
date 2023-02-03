package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.dto.AdminUpdateEventRequest;
import ru.practicum.ewm.event.dto.EventFullDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EventAdminService {
    EventFullDto publish(Long eventId);

    EventFullDto reject(Long eventId);

    EventFullDto put(Long eventId, AdminUpdateEventRequest adminUpdateEventRequest);

    List<EventFullDto> get(List<Long> users, List<String> states, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);
}
