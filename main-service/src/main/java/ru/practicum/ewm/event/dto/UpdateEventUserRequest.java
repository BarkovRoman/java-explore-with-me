package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.event.model.Location;
import ru.practicum.ewm.event.model.State;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventUserRequest {
    @NotBlank()
    @Size(min = 20, max = 2000, message = "annotation менее 20 или более 2000")
    private String annotation;
    private Long category;
    @NotBlank()
    @Size(min = 20, max = 7000, message = "description менее 20 или более 7000")
    private String description;
    private String eventDate; // "yyyy-MM-dd HH:mm:ss"
    private Location location;   // координаты необходимо изменить тип
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private State stateAction;
    @NotBlank()
    @Size(min = 3, max = 120, message = "title менее 3 или более 120")
    private String title;
}
