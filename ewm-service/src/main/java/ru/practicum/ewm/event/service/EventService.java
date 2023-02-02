package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.dto.*;

import java.util.List;

public interface EventService {
    EventFullDto add(NewEventDto newEventDto, Long userId);

    EventFullDto update(NewEventDto newEventDto, Long userId);

    EventFullDto cancellationById(Long userId, Long eventId);

    ParticipationRequestDto confirmByUserIdByEventId(Long userId, Long eventId, Long reqId);

    ParticipationRequestDto rejectByUserIdByEventId(Long userId, Long eventId, Long reqId);

    List<EventShortDto> getAllEventByUserId(Long userId, Integer from, Integer size);

    EventShortDto getEventByUserId(Long userId, Long eventId);

    ParticipationRequestDto getEventByRequests(Long userId, Long eventId);
}
