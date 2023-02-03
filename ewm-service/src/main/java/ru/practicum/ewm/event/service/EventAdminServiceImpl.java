package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.dto.AdminUpdateEventRequest;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.State;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.ExistingValidationException;
import ru.practicum.ewm.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventAdminServiceImpl implements EventAdminService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    @Override
    @Transactional
    public EventFullDto publish(Long eventId) {
        Event event = getById(eventId);
        if (event.getEventDate().isBefore(LocalDateTime.now().minusHours(1)) && !event.getState().equals(State.PENDING)) {
            throw new ExistingValidationException("Невозможно опубликовать событие");
        }
        event.setState(State.PUBLISHED);
        event.setPublishedOn(LocalDateTime.now());
        log.info("Publish Event={}", event);
        return eventMapper.toEventFullDto(event);
    }

    @Override
    @Transactional
    public EventFullDto reject(Long eventId) {
        Event event = getById(eventId);
        if (event.getState().equals(State.PUBLISHED)) {
            throw new ExistingValidationException("Невозможно отменить событие, опубликованное");
        }
        event.setState(State.CANCELED);
        log.info("Reject Event={}", event);
        return eventMapper.toEventFullDto(event);
    }

    @Override
    @Transactional
    public EventFullDto put(Long eventId, AdminUpdateEventRequest adminUpdateEventRequest) {
        Event event = getById(eventId);
        event = eventMapper.updateEvent(adminUpdateEventRequest);
        log.info("Put Event={}", event);
        return eventMapper.toEventFullDto(event);
    }

    @Override
    public List<EventFullDto> get(List<Long> users, List<String> states, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
        final PageRequest page = PageRequest.of(from, size);
        List<Event> events = eventRepository.findEventByInitiatorIdAndStateAndCategory_IdAndEventDateBetween(users, states, categories, rangeStart, rangeEnd, page);
        return events.stream()
                .map(eventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    private Event getById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Event id=%s not found", id)));

    }
}
