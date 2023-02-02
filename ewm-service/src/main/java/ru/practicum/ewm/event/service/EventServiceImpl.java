package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.categories.model.Category;
import ru.practicum.ewm.categories.repository.CategoryRepository;
import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.State;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventMapper eventMapper;

    @Override
    @Transactional
    public EventFullDto add(NewEventDto newEventDto, Long userId) {
        User initiator = isExistsUserById(userId);
        Category category = isExistsCategoryById(newEventDto.getCategory());
        Event event = eventMapper.toEvent(newEventDto, initiator, category);
        Event newEvent = eventRepository.save(event);
        log.info("Add Event={}, userId={}", newEvent, userId);
        return eventMapper.toEventFullDto(newEvent);
    }

    @Override
    @Transactional
    public EventFullDto update(NewEventDto newEventDto, Long userId) {
        return null;
    }

    @Override
    @Transactional
    public EventFullDto cancellationById(Long userId, Long eventId) {
        isExistsUserById(userId);
        Event event = isExistsEventById(eventId);

        if (event.getState().equals(State.PENDING) && event.getInitiator().getId().equals(userId)) {
            event.setState(State.PENDING);
        }
        log.info("Update EventStatus -> {}, userId={}", State.PENDING, userId);
        return eventMapper.toEventFullDto(event);
    }

    @Override
    @Transactional
    public ParticipationRequestDto confirmByUserIdByEventId(Long userId, Long eventId, Long reqId) {
        isExistsUserById(userId);
        Event event = isExistsEventById(eventId);

        if (event.getParticipantLimit().equals(0) || !event.getRequestModeration()) {
            log.info("Confirm Event limit={}, moderation={}", event.getParticipantLimit(), event.getRequestModeration());
            return eventMapper.toParticipationRequestDto(event, reqId);
        }  // если для события лимит заявок равен 0 или отключена пре-модерация заявок, то подтверждение заявок не требуется

        if (event.getParticipantLimit().equals(event.getConfirmedRequests())) {
            log.info("Confirm Event Limit={}, ConfirmedRequests={}", event.getParticipantLimit(), event.getConfirmedRequests());
            return eventMapper.toParticipationRequestDto(event, reqId);
        }  // нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие
        return null;
    }

    @Override
    @Transactional
    public ParticipationRequestDto rejectByUserIdByEventId(Long userId, Long eventId, Long reqId) {
        return null;
    }

    @Override
    public List<EventShortDto> getAllEventByUserId(Long userId, Integer from, Integer size) {
        isExistsUserById(userId);
        final PageRequest page = PageRequest.of(from, size);
        List<EventShort> events = eventRepository.findEventByInitiatorId(userId, page);
        return events.stream()
                .map(eventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto getEventByUserId(Long userId, Long eventId) {
        isExistsUserById(userId);
        Event event = eventRepository.findByIdAndInitiatorId(userId, eventId);
        return eventMapper.toEventFullDto(event);
    }

    @Override
    public ParticipationRequestDto getEventByRequests(Long userId, Long eventId) {
        return null;
    }

    private User isExistsUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User id=%s not found", id)));
    }

    private Category isExistsCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Category id=%s not found", id)));
    }

    private Event isExistsEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Event id=%s not found", id)));
    }


}
