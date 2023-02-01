package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.dto.ParticipationRequestDto;
import ru.practicum.ewm.event.dto.ResponseEventDto;
import ru.practicum.ewm.event.dto.UpdateEventDto;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.service.EventService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("users/")
public class EventPrivateController {
    private final EventService eventService;

    @PostMapping("/{userId}/events")
    public ResponseEventDto add(@Valid @RequestBody NewEventDto newEventDto,
                                @PathVariable Long userId) {
        log.info("Create Event={}, userId={}", newEventDto, userId);
        return eventService.add(newEventDto, userId);
    }

    @PatchMapping("/{userId}/events")
    public ResponseEventDto update(@Valid @RequestBody UpdateEventDto updateEventDto,
                                   @PathVariable Long userId) {
        log.info("Update Event={}, userId={}", updateEventDto, userId);
        return eventService.update(updateEventDto, userId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public ResponseEventDto cancellationById(@PathVariable Long userId,
                                              @PathVariable Long eventId) {
        log.info("Cancellation eventId={}, userId={}", eventId, userId);
        return eventService.cancellationById(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto participationByUserIdByEventId(@PathVariable Long userId,
                                                                  @PathVariable Long eventId,
                                                                  @PathVariable Long reqId) {
        log.info("Participation eventId={}, userId={}, reqId={}", eventId, userId, reqId);
        return eventService.participationByUserIdByEventId(userId, eventId, reqId);
    }



    @GetMapping("/compilations")
    public Event getEvent() {
        return null;
    }

}
