package ru.practicum.ewm.event.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.event.model.Event;

@Mapper(componentModel = "spring")
public interface EventMapping {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "participantLimit", )
    Event toEvent(NewEventDto newEventDto);

}
