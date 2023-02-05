package ru.practicum.ewm.event.dto;

import org.mapstruct.*;
import ru.practicum.ewm.categories.dto.CategoryMapper;
import ru.practicum.ewm.categories.model.Category;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.dto.UserMapper;
import ru.practicum.ewm.user.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CategoryMapper.class, UserMapper.class})
public interface EventMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "paid", defaultValue = "false")
    @Mapping(target = "participantLimit", defaultValue = "0")
    @Mapping(target = "confirmedRequests", constant = "0")
    @Mapping(target = "requestModeration", defaultValue = "true")
    @Mapping(target = "state", constant = "PENDING")
    @Mapping(target = "category", source = "categories")
    @Mapping(target = "initiator", source = "initiator")
    @Mapping(target = "eventDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    Event toEvent(NewEventDto newEventDto, User initiator, Category categories);

    @Mapping(target = "eventDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "createdOn", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "publishedOn", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "category", source = "event.category")
    @Mapping(target = "initiator", source = "event.initiator")
    EventFullDto toEventFullDto(Event event);

    @Mapping(target = "eventDate", /*source = "event.eventDate",*/ dateFormat = "yyyy-MM-dd HH:mm:ss")
    EventShortDto toEventShortDto(Event event);

    @Mapping(target = "eventDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "category", source = "categories")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Event updateEvent(UpdateEventUserRequest updateEventUserRequest, @MappingTarget Event event, Category categories);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "eventDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Event updateEvent(AdminUpdateEventRequest adminUpdateEventRequest, @MappingTarget Event event);

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
