package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class EventPrivateController {
    private final EventService eventService;

    @PostMapping("/{userId}/events")
    public EventFullDto add(@Validated(Create.class) @RequestBody NewEventDto newEventDto,
                            @PathVariable Long userId) {
        log.info("Create Event={}, userId={}", newEventDto, userId);
        return eventService.add(newEventDto, userId);
    }

    @PatchMapping("/{userId}/events")
    public EventFullDto update(@Validated(Update.class) NewEventDto newEventDto,
                               @PathVariable Long userId) {
        log.info("Update Event={}, userId={}", newEventDto, userId);
        return eventService.update(newEventDto, userId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto cancellationById(@PathVariable Long userId,
                                         @PathVariable Long eventId) {
        log.info("Cancellation eventId={}, userId={}", eventId, userId);
        return eventService.cancellationById(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmByUserIdByEventId(@PathVariable Long userId,
                                                            @PathVariable Long eventId,
                                                            @PathVariable Long reqId) {
        log.info("Confirm eventId={}, userId={}, reqId={}", eventId, userId, reqId);
        return eventService.confirmByUserIdByEventId(userId, eventId, reqId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectByUserIdByEventId(@PathVariable Long userId,
                                                           @PathVariable Long eventId,
                                                           @PathVariable Long reqId) {
        log.info("Reject eventId={}, userId={}, reqId={}", eventId, userId, reqId);
        return eventService.rejectByUserIdByEventId(userId, eventId, reqId);
    }

    @GetMapping("{userId}/events")
    public List<EventShortDto> getAllEventByUserId(@PathVariable Long userId,
                                                   @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                   @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("getAllEvent userId={}, from={}, size={}", userId, from, size);
        return eventService.getAllEventByUserId(userId, from, size);
    }

    @GetMapping("{userId}/events/{eventId}")
    public EventFullDto getEventByUserId(@PathVariable Long userId,
                                          @PathVariable Long eventId) {
        log.info("getEvent eventId={}, userId={}", eventId, userId);
        return eventService.getEventByUserId(userId, eventId);
    }

    @GetMapping("{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getEventByRequests(@PathVariable Long userId,
                                                      @PathVariable Long eventId) {
        log.info("getEventByRequests eventId={}, userId={}", eventId, userId);
        return eventService.getEventByRequests(userId, eventId);
    }
}
