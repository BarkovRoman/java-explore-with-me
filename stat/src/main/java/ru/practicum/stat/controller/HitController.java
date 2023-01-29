package ru.practicum.stat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stat.dto.CreateHitDto;
import ru.practicum.stat.dto.ResponseHitDto;
import ru.practicum.stat.dto.ResponseStatDto;
import ru.practicum.stat.service.HitService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HitController {
    private final HitService hitService;

    @PostMapping("/hit")
    public ResponseHitDto create(@RequestBody CreateHitDto createHitDto) {
        log.info("Create Hit {}", createHitDto.toString());
        return hitService.create(createHitDto);
    }

    @GetMapping("/stats")
    public List<ResponseStatDto> getStat(@RequestParam String start,
                                     @RequestParam String end,
                                     @RequestParam(defaultValue = "false") Boolean unique,
                                     @RequestParam List<String> uris) {
        log.info("Get stats: start={}, end={}, unique={}, uris={}", start, end, unique, uris);
        return hitService.get(start, end, unique, uris);
    }
}
