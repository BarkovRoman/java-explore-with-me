package ru.practicum.ewm.compilations.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.event.dto.EventShortDto;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompilationDto {
    private Long id;
    private List<EventShortDto> events;
    private Boolean pinned;
    private String title;
}
