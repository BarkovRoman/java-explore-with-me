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
    public EventFullDto patch(Long eventId, AdminUpdateEventRequest updateEvent) {
        Event event = getEventById(eventId);

        if (event.getEventDate().isBefore(LocalDateTime.now().minusHours(1))) {
            log.error("Невозможно изменить событие");
            throw new ExistingValidationException("Невозможно опубликовать событие");
        }

        switch (updateEvent.getStateAction()) {
            case PUBLISH_EVENT:
                if (!event.getState().equals(State.PENDING)) {
                    log.error("Невозможно опубликовать событие");
                    throw new ExistingValidationException("Невозможно опубликовать событие");
                }
                event.setState(State.PUBLISHED);
                break;
            case REJECT_EVENT:
                if (event.getState().equals(State.PUBLISHED)) {
                    log.error("Невозможно отменить событие");
                    throw new ExistingValidationException("Невозможно отменить событие");
                }
                event.setState(State.CANCELED);
                break;
        }

        event = eventMapper.updateEvent(updateEvent, event);
        log.info("Put EventId={}", event.getId());
        return eventMapper.toEventFullDto(event, event.getRequests().size());
    }

    @Override
    public List<EventFullDto> get(List<Long> users, List<State> states, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
        final PageRequest page = PageRequest.of(from, size);
        List<Event> events = eventRepository.findEventByInitiatorIdAndStateAndCategory_IdAndEventDateBetween(users, states, categories, rangeStart, rangeEnd, page);
        return events.stream()
                .map(event -> eventMapper.toEventFullDto(event, event.getRequests().size()))
                .collect(Collectors.toList());
    }

    private Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Event id=%s not found", id)));

    }
}
