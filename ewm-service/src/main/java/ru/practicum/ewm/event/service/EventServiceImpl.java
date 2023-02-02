package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.categories.model.Category;
import ru.practicum.ewm.categories.repository.CategoryRepository;
import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public EventFullDto add(NewEventDto newEventDto, Long userId) {
        return null;
    }

    @Override
    public EventFullDto update(UpdateEventDto updateEventDto, Long userId) {
        return null;
    }

    @Override
    public EventFullDto cancellationById(Long userId, Long eventId) {
        return null;
    }

    @Override
    public ParticipationRequestDto confirmByUserIdByEventId(Long userId, Long eventId, Long reqId) {
        return null;
    }

    @Override
    public ParticipationRequestDto rejectByUserIdByEventId(Long userId, Long eventId, Long reqId) {
        return null;
    }

    @Override
    public List<EventShortDto> getAllEventByUserId(Long userId, Integer from, Integer size) {
        return null;
    }

    @Override
    public EventShortDto getEventByUserId(Long userId, Long eventId) {
        return null;
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


}
