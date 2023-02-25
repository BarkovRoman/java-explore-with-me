package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.ewm.event.dto.AdminUpdateEventRequest;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.State;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.event.util.EventUtil;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.request.model.RequestStatus;
import ru.practicum.ewm.request.repository.RequestRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventAdminServiceImpl implements EventAdminService {

    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final EventMapper eventMapper;
    private final StatsClient client;

    @Override
    @Transactional
    public EventFullDto patch(Long eventId, AdminUpdateEventRequest updateEvent) {
        Event event = getEventById(eventId);

        if (event.getEventDate().isBefore(LocalDateTime.now().minusHours(1))) {
            log.error("EventDate={},  LocalDateTime.now={} min 1 hours", event.getEventDate(), LocalDateTime.now());
            throw new ConflictException("Дата начала изменяемого события должна быть не ранее чем за час от даты публикации");
        }

        switch (updateEvent.getStateAction()) {
            case PUBLISH_EVENT:
                if (!event.getState().equals(State.PENDING)) {
                    log.error("Невозможно опубликовать событие. Status={}", event.getState());
                    throw new ConflictException("Событие можно публиковать, только если оно в состоянии ожидания публикации");
                }
                event.setState(State.PUBLISHED);
                break;
            case REJECT_EVENT:
                if (event.getState().equals(State.PUBLISHED)) {
                    log.error("Невозможно отменить событие. Status={}", event.getState());
                    throw new ConflictException("Событие можно отклонить, только если оно еще не опубликовано ");
                }
                event.setState(State.CANCELED);
                break;
        }
        event.setViews(client.getViews(event.getId()));

        event = eventMapper.updateEvent(updateEvent, event);

        Long confirmedRequests = requestRepository.countByEventAndStatus(eventId, RequestStatus.CONFIRMED);

        event.setConfirmedRequests(confirmedRequests);
        log.info("Put BD EventId={}", event);
        return eventMapper.toEventFullDto(event);
    }

    @Override
    public List<EventFullDto> get(List<Long> users, List<State> states, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
        final PageRequest page = PageRequest.of(from, size);
        List<Event> events = eventRepository.findEventByInitiatorIdAndStateAndCategory_IdAndEventDateBetween(users, states, categories, rangeStart, rangeEnd, page);
        EventUtil.addViews(events, client);
        EventUtil.addConfirmedRequests(events, requestRepository);
        return events.stream()
                .map(eventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    private Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Event id=%s not found", id)));

    }
}
