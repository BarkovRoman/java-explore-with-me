package ru.practicum.stat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.stat.dto.CreateHitDto;
import ru.practicum.stat.dto.HitMapper;
import ru.practicum.stat.dto.ResponseHitDto;
import ru.practicum.stat.model.Hit;
import ru.practicum.stat.repositry.HitRepositry;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {

    private final HitRepositry hitRepositry;
    private final HitMapper mapper;

    @Override
    @Transactional
    public ResponseHitDto create(CreateHitDto createHitDto) {
        Hit hit = hitRepositry.save(mapper.toHit(createHitDto));
        log.info("Add Hit={}", hit);
        return mapper.toResponseHitDto(hit);
    }

    @Override
    public ResponseStatus get(LocalDateTime start, LocalDateTime end, Boolean unique, List<String> uris) {
        return null;
    }
}
