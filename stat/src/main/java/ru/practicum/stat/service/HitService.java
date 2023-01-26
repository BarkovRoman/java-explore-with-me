package ru.practicum.stat.service;

import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.stat.dto.CreateHitDto;
import ru.practicum.stat.dto.ResponseHitDto;

import java.time.LocalDateTime;
import java.util.List;

public interface HitService {
    ResponseHitDto create(CreateHitDto createHitDto);

    ResponseStatus get(LocalDateTime start, LocalDateTime end, Boolean unique, List<String> uris);
}
