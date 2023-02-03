package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.location.model.Location;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminUpdateEventRequest {
    private Long id;
    private String annotation;
    private Long category;
    private String description;
    private String eventDate; // "yyyy-MM-dd HH:mm:ss"
    private Location location;   // координаты необходимо изменить тип
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private String title;
}
