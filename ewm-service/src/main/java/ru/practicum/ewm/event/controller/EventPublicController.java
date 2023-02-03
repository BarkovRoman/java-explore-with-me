package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.model.Sort;
import ru.practicum.ewm.event.service.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventPublicController {
    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> get(@RequestParam(required = false) String text,
                             @RequestParam(required = false) List<Long> categories,
                             @RequestParam(required = false) Boolean paid,
                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                             @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                             @RequestParam Sort sort,
                             @PositiveOrZero @RequestParam (defaultValue = "0") Integer from,
                             @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Get Event text={}, paid={}, rangeStart={}, rangeEnd={}, onlyAvailable={}, sort={}, from={}, size{}, /n categories={}",
                text, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, categories);
        // Отправить запрос клиенту на добавление в статистику
        return eventService.get(text, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, categories);
    }

    @GetMapping("/{id}")
    public EventFullDto getById(@PathVariable Long id) {
        log.info("Get Event eventId={}", id);
        // Отправить запрос клиенту на добавление в статистику
        return eventService.getById(id);
    }
}
