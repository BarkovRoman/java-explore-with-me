package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventDto {
    private Long id;
    @NotBlank
    @Size(min = 20, max = 2000, message = "annotation менее 20 или более 2000")
    private String annotation;
    private Integer category;
    @NotBlank
    @Size(min = 20, max = 7000, message = "description менее 20 или более 7000")
    private String description;
    private String eventDate; // "yyyy-MM-dd HH:mm:ss"
    private Boolean paid;
    private Integer participantLimit;
    @NotBlank
    @Size(min = 3, max = 120, message = "title менее 3 или более 120")
    private String title;
}
