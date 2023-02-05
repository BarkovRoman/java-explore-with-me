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
import ru.practicum.ewm.exception.ExistingValidationException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.dto.RequestMapper;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.repository.RequestRepository;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventPrivateServiceImpl implements EventPrivateService {
    private final RequestRepository requestRepository;

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventMapper eventMapper;
    private final RequestMapper requestMapper;

    @Override
    @Transactional
    public EventFullDto add(NewEventDto newEventDto, Long userId) {
        User initiator = isExistsUserById(userId);
        Category category = isExistsCategoryById(newEventDto.getCategory());
        Event event = eventMapper.toEvent(newEventDto, initiator, category);
        Event newEvent = eventRepository.save(event);
        log.info("Add BD EventId={}, userId={}", newEvent.getId(), userId);
        return eventMapper.toEventFullDto(newEvent);
    }

    @Override
    @Transactional
    public EventFullDto update(Long userId, Long eventId, UpdateEventUserRequest updateEvent) {
        isExistsUserById(userId);
        Event event = isExistsEventByIdAndInitiatorId(eventId, userId);
        Category category = null;

        if (!event.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {

            if (event.getRequestModeration() || event.getState().equals(State.CANCELED)) {

                if (updateEvent.getCategory() != null) {
                    category = isExistsCategoryById(updateEvent.getCategory());
                }
                if (updateEvent.getStateAction().equals(State.CANCEL_REVIEW)) event.setState(State.CANCELED);

                event = eventMapper.updateEvent(updateEvent, event, category);

                log.info("Update EventStatus -> {}, userId={}", State.CANCELED, userId);
                return eventMapper.toEventFullDto(event);
            }
        }

        throw new ExistingValidationException("Невозможно отменить событие");
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateStatus(Long userId, Long eventId, EventRequestStatusUpdateRequest statusUpdateRequest) {
        List<ParticipationRequestDto> confirmed = new ArrayList<>();
        List<ParticipationRequestDto> rejected = new ArrayList<>();

        isExistsUserById(userId);
        Event event = isExistsEventByIdAndInitiatorId(eventId, userId);
        List<Request> requests = requestRepository.findRequestsByEventIn(statusUpdateRequest.getRequestIds());

        // если для события лимит заявок равен 0 или отключена пре-модерация заявок, то подтверждение заявок не требуется
        if (event.getParticipantLimit().equals(0) || !event.getRequestModeration()) {
            log.info("Confirm Event limit={}, moderation={}", event.getParticipantLimit(), event.getRequestModeration());
            confirmed = requests.stream()
                    .peek(request -> request.setStatus(statusUpdateRequest.getStatus()))
                    .map(requestMapper::toParticipationRequestDto)
                    .collect(Collectors.toList());
            return new EventRequestStatusUpdateResult(confirmed, rejected);
        }

        // нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие
        if (event.getParticipantLimit().equals(event.getConfirmedRequests())) {
            log.error("Confirm Event Limit={}, ConfirmedRequests={}", event.getParticipantLimit(), event.getConfirmedRequests());
            throw new ExistingValidationException("Невозможно подтвердить заявку, достигнут лимит.");
        }

        for (Request req: requests) {
            if (req.getStatus().equals((State.PENDING))) {
                if (!event.getParticipantLimit().equals(event.getConfirmedRequests())) {
                    req.setStatus(State.CONFIRMED);
                    confirmed.add(requestMapper.toParticipationRequestDto(req));
                    event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                }
                req.setStatus(State.REJECTED);
                rejected.add(requestMapper.toParticipationRequestDto(req));
            }
        }
        return new EventRequestStatusUpdateResult(confirmed, rejected);
    }

    @Override
    public List<EventShortDto> getAllEventByUserId(Long userId, Integer from, Integer size) {
        isExistsUserById(userId);
        final PageRequest page = PageRequest.of(from, size);
        List<Event> events = eventRepository.findEventByInitiatorId(userId, page);
        log.info("Get all events={}", events);
        return events.stream()
                .map(eventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto getEventByUserId(Long userId, Long eventId) {
        isExistsUserById(userId);
        Event event = isExistsEventByIdAndInitiatorId(eventId, userId);
        log.info("Get Event event={} by userId={}", event, userId);
        return eventMapper.toEventFullDto(event);
    }

    @Override
    public List<ParticipationRequestDto> getEventByRequests(Long userId, Long eventId) {
        isExistsUserById(userId);
        isExistsEventByIdAndInitiatorId(eventId, userId);
        List<Request> requests = requestRepository.findByEvent(eventId);
        log.info("Get Requests={} by eventId={}, userid={}", requests, eventId, userId);

        return requests.stream()
                .map(requestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    private User isExistsUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User id=%s not found", id)));
    }

    private Category isExistsCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Category id=%s not found", id)));
    }

    private Event isExistsEventByIdAndInitiatorId(Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event id=%s not found", eventId)));

        if (!event.getInitiator().getId().equals(userId)) {
            throw new NotFoundException(String.format("The event id=%s does not belong to the user id=%s", eventId, userId));
        }
        return event;
    }
}
