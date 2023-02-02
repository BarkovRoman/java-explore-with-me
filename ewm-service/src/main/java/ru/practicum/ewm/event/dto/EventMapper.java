package ru.practicum.ewm.event.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.categories.model.Category;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.user.model.User;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "paid", defaultValue = "false")
    @Mapping(target = "participantLimit", defaultValue = "0")
    @Mapping(target = "requestModeration", defaultValue = "true")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "initiator", source = "initiator")
    Event toEvent(NewEventDto newEventDto, User initiator, Category category);

    EventFullDto toEventFullDto(Event event);

    @Mapping(target = "requester", source = "requester")
    ParticipationRequestDto toParticipationRequestDto(Event event, Long requester);

    EventShortDto toEventShortDto(EventShort eventShort);

}
