package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventMapper;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.Sort;
import ru.practicum.ewm.event.model.State;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.event.util.EventUtil;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.request.model.RequestStatus;
import ru.practicum.ewm.request.repository.RequestRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventPublicServiceImpl implements EventPublicService {
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final EventMapper eventMapper;
    private final StatsClient client;

    @Override
    @Transactional
    public List<EventShortDto> get(String text, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                   Boolean onlyAvailable, Sort sort, Integer from, Integer size, List<Long> categories) {
        List<Event> events;
        if (sort == null) {
            return new ArrayList<>();
        }

        switch (sort) {
            case VIEWS: // сортировка по просмотрам
                events = eventRepository.findEventByFilterViews(text, paid, rangeStart, rangeEnd,
                        onlyAvailable, categories);
                EventUtil.addViews(events, client);
                events = events.stream()
                        .sorted(Comparator.comparingLong(Event::getViews).reversed())
                        .skip(from)
                        .limit(size)
                        .collect(toList());
                break;
            case EVENT_DATE: // сортировка по дате
                events = eventRepository.findEventByFilterDate(text, paid, rangeStart, rangeEnd,
                        onlyAvailable, from, size, categories);

                EventUtil.addViews(events, client);
                break;
            default:
                throw new NotFoundException("Event must be published");
        }
        EventUtil.addConfirmedRequests(events, requestRepository);
        log.info("Get Events={}", events);
        return events.stream()
                .map(eventMapper::toEventShortDto)
                .collect(toList());
    }

    @Override
    public EventFullDto getById(Long id) {
        Event event = eventRepository.getByIdAndState(id, State.PUBLISHED);
        event.setViews(client.getViews(event.getId()));
        event.setConfirmedRequests(requestRepository.countByEventAndStatus(id, RequestStatus.CONFIRMED));
        log.info("Get Event={}", event);
        return eventMapper.toEventFullDto(event);
    }
}