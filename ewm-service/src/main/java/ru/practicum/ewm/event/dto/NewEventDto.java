package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.event.model.Location;
import ru.practicum.ewm.exception.validation.CreatedValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@CreatedValid
public class NewEventDto {
    @NotNull(groups = Update.class)
    Long id;
    @NotBlank(groups = {Create.class})
    @Size(groups = {Create.class},min = 20, max = 2000, message = "annotation менее 20 или более 2000")
    private String annotation;
    @NotNull(groups = {Create.class})
    private Long category;
    @NotBlank(groups = {Create.class})
    @Size(groups = {Create.class}, min = 20, max = 7000, message = "description менее 20 или более 7000")
    private String description;
    private String eventDate; // "yyyy-MM-dd HH:mm:ss"
    @NotNull(groups = {Create.class})
    private Location location;   // координаты необходимо изменить тип
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    @NotBlank(groups = {Create.class})
    @Size(groups = {Create.class}, min = 3, max = 120, message = "title менее 3 или более 120")
    private String title;
}
