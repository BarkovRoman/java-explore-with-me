package ru.practicum.stat.service;

import ru.practicum.stat.dto.CreateHitDto;
import ru.practicum.stat.dto.ResponseHitDto;
import ru.practicum.stat.dto.ResponseStatDto;

import java.util.List;

public interface HitService {
    ResponseHitDto create(CreateHitDto createHitDto);

    List<ResponseStatDto> get(String start, String end, Boolean unique, List<String> uris);
}
