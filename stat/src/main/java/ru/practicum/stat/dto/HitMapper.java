package ru.practicum.stat.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.stat.model.Hit;

@Mapper(componentModel = "spring")
public interface HitMapper {
    @Mapping(target = "id", ignore = true)
    Hit toHit(CreateHitDto createHitDto);

    @Mapping(target = "created", defaultValue = "yyyy-MM-dd HH:mm:ss")
    ResponseHitDto toResponseHitDto(Hit hit);
}
