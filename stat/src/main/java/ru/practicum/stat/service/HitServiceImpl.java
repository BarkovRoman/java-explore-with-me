package ru.practicum.stat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stat.dto.CreateHitDto;
import ru.practicum.stat.dto.HitMapper;
import ru.practicum.stat.dto.ResponseHitDto;
import ru.practicum.stat.dto.ResponseStatDto;
import ru.practicum.stat.exception.RequestParametersException;
import ru.practicum.stat.model.Hit;
import ru.practicum.stat.repositry.AppRepositry;
import ru.practicum.stat.repositry.HitRepositry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {

    private final HitRepositry hitRepositry;
    private final AppRepositry appRepositry;
    private final HitMapper mapper;

    @Override
    @Transactional
    public ResponseHitDto createHit(CreateHitDto createHitDto) {
        Long idApp;
        if (appRepositry.existsByNameIgnoreCase(createHitDto.getApp())) {
            idApp = appRepositry.
        }
        Hit hit = hitRepositry.save(mapper.toHit(createHitDto));
        log.info("Add Hit={}", hit);
        return mapper.toResponseHitDto(hit);
    }

    @Override
    public List<ResponseStatDto> getStats(LocalDateTime start, LocalDateTime end, Boolean unique, List<String> uris) {

        if (!start.isBefore(end)) {
            throw new RequestParametersException(String.format("Error Start=%tc, End=%tc", start, end));
        }
        if (unique) {
            return hitRepositry.statByUniqueIp(start, end, uris);
        }
        return hitRepositry.statByIp(start, end, uris);
    }
}
