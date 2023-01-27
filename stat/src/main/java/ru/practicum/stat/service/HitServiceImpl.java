package ru.practicum.stat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stat.dto.CreateHitDto;
import ru.practicum.stat.dto.HitMapper;
import ru.practicum.stat.dto.ResponseHitDto;
import ru.practicum.stat.dto.ResponseStatDto;
import ru.practicum.stat.exception.IllegalRequestException;
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
    public ResponseStatDto get(LocalDateTime start, LocalDateTime end, Boolean unique, List<String> uris) {
        if (start.isBefore(end) || start == end) {
            throw new IllegalRequestException(String.format("Error Start=%tc, End=%tc", start, end));
        }
        if (unique) {
            return hitRepositry.findByStatsByDistinct(start, end, uris);
        }
        return hitRepositry.findByStats(start, end, uris);
    }
}
