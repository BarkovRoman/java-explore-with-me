package ru.practicum.ewm.event.dto;

import org.mapstruct.*;
import ru.practicum.ewm.categories.dto.CategoryDto;
import ru.practicum.ewm.categories.model.Category;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.user.dto.UserShortDto;
import ru.practicum.ewm.user.model.User;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "paid", defaultValue = "false")
    @Mapping(target = "participantLimit", defaultValue = "0")
    @Mapping(target = "confirmedRequests", defaultValue = "0", ignore = true)
    @Mapping(target = "requestModeration", defaultValue = "true")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "initiator", source = "initiator")
    Event toEvent(NewEventDto newEventDto, User initiator, Category category);

   /* @Mapping(target = "eventDate", source = "event.eventDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "createdOn", source = "event.createdOn", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "publishedOn", source = "event.publishedOn", dateFormat = "yyyy-MM-dd HH:mm:ss")*/
    EventFullDto toEventFullDto(Event event);

    //@Mapping(target = "eventDate", source = "event.eventDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    EventShortDto toEventShortDto(Event event);

    @Mapping(target = "category", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Event updateEvent(NewEventDto newEventDto, @MappingTarget Event event);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    Event updateEvent(AdminUpdateEventRequest adminUpdateEventRequest);

    /*List<EventShortDto> mapEventsDto(List<Event> events);
    default EventShortDto mapEventsDto(Event event) {
        return new EventShortDto(event.getId(),
                event.getAnnotation(),
                new CategoryDto(event.getCategory().getId(), event.getCategory().getName()),
                event.getConfirmedRequests(),
                event.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                new UserShortDto(event.getInitiator().getId(), event.getInitiator().getName()),
                event.getPaid(),
                event.getTitle(),
                event.getViews());
    }*/
}
