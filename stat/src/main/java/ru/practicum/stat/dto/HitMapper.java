package ru.practicum.stat.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.stat.model.Hit;

@Mapper(componentModel = "spring")
public interface HitMapper {
    @Mapping(target = "id", ignore = true)
    Hit toHit(CreateHitDto createHitDto);

    ResponseHitDto toResponseHitDto(Hit hit);

    @Mapping(target = "hits", source = "hitCount")
    ResponseStatDto toResponseStatDto(Hit hit, int hitCount);
}
