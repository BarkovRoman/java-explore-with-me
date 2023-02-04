package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.categories.model.Category;
import ru.practicum.ewm.event.model.State;
import ru.practicum.ewm.event.model.Location;
import ru.practicum.ewm.user.model.User;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventFullDto {
    private Long id;
    private String annotation;
    private Category category;
    private Integer confirmedRequests;
    private String createdOn;
    private String description;
    private String eventDate;
    private User initiator;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private String publishedOn;
    private Boolean requestModeration;
    private State state;
    private String title;
    private Integer views;
}
