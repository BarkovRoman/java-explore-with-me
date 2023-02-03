package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.event.model.Sort;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    EventFullDto add(NewEventDto newEventDto, Long userId);

    EventFullDto update(NewEventDto newEventDto, Long userId);

    EventFullDto cancellationById(Long userId, Long eventId);

    ParticipationRequestDto confirmByUserIdByEventId(Long userId, Long eventId, Long reqId);

    ParticipationRequestDto rejectByUserIdByEventId(Long userId, Long eventId, Long reqId);

    List<EventShortDto> getAllEventByUserId(Long userId, Integer from, Integer size);

    EventFullDto getEventByUserId(Long userId, Long eventId);

    List<ParticipationRequestDto> getEventByRequests(Long userId, Long eventId);

    List<EventShortDto> get(String text, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, Sort sort, Integer from, Integer size, List<Long> categories);

    EventFullDto getById(Long id);
}
