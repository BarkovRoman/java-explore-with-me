package ru.practicum.ewm.event.service;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventMapper;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.QEvent;
import ru.practicum.ewm.event.model.Sort;
import ru.practicum.ewm.event.model.State;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.event.util.EventUtil;
import ru.practicum.ewm.request.model.RequestStatus;
import ru.practicum.ewm.request.repository.RequestRepository;

import java.time.LocalDateTime;
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
        final PageRequest page = PageRequest.of(from, size);
        rangeStart = rangeStart == null ? LocalDateTime.now() : rangeStart;
        rangeEnd = rangeEnd == null ? rangeStart.plusWeeks(1) : rangeEnd;

        BooleanBuilder predicate = new BooleanBuilder();

        if (text != null) {
            predicate.and(QEvent.event.annotation.likeIgnoreCase(text).or(QEvent.event.title.likeIgnoreCase(text)));
        }
        if (paid != null) {
            predicate.and(QEvent.event.paid.eq(paid));
        }
        predicate.and(QEvent.event.eventDate.between(rangeStart, rangeEnd))
                .and(QEvent.event.category.id.in(categories))
                .and(QEvent.event.state.eq(State.PUBLISHED));
        if (onlyAvailable != null) {
            predicate.and(QEvent.event.participantLimit.eq(0));
                  //  .or(QEvent.event.participantLimit.gt(QEvent.event.requests)));
        }


        List<Event> events = onlyAvailable ? eventRepository.findEventByAvailable(text, paid, rangeStart, rangeEnd, categories, page) :
                eventRepository.findEvent(text, paid, rangeStart, rangeEnd, categories, page);
        EventUtil.addViews(events, client);
        EventUtil.addConfirmedRequests(events, requestRepository);

        if (sort == null) sort = Sort.ALL;
        switch (sort) {
            case VIEWS: // сортировка по просмотрам
                events.sort(Comparator.comparing(Event::getViews));
                break;
            case EVENT_DATE: // сортировка по дате
                events.sort(Comparator.comparing(Event::getEventDate));
                break;
        }

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
