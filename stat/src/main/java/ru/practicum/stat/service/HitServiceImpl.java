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
    private final HitMapper mapper;

    @Override
    @Transactional
    public ResponseHitDto create(CreateHitDto createHitDto) {
        Hit hit = hitRepositry.save(mapper.toHit(createHitDto));
        log.info("Add Hit={}", hit);
        return mapper.toResponseHitDto(hit);
    }

    @Override
    public List<ResponseStatDto> get(String start, String end, Boolean unique, List<String> uris) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(start, format);
        LocalDateTime endTime = LocalDateTime.parse(end, format);

        if (startTime.isAfter(endTime) || startTime == endTime) {
            throw new RequestParametersException(String.format("Error Start=%tc, End=%tc", startTime, endTime));
        }
        if (unique) {
            return hitRepositry.statByUniqueIp(startTime, endTime, uris);
        }
        return hitRepositry.statByIp(startTime, endTime, uris);
    }
}
