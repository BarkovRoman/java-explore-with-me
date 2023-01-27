package ru.practicum.stat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stat.dto.CreateHitDto;
import ru.practicum.stat.dto.ResponseHitDto;
import ru.practicum.stat.dto.ResponseStatDto;
import ru.practicum.stat.service.HitServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HitController {
    private final HitServiceImpl hitService;
    @PostMapping("/hit")
    public ResponseHitDto create(@RequestBody CreateHitDto createHitDto) {
        log.info("Create Hit {}", createHitDto);
        return hitService.create(createHitDto);
    }

    @GetMapping("/stats")
    public ResponseStatDto get(@RequestParam  LocalDateTime start,
                                     @RequestParam  LocalDateTime end,
                                     @RequestParam Boolean unique,
                                     List<String> uris) {
        log.info("Get stats: start={}, end={}, unique={}", start, end, unique);
        return hitService.get(start, end, unique, uris);
    }
}
