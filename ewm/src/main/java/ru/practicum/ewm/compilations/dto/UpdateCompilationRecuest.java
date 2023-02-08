package ru.practicum.ewm.compilations.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCompilationRecuest {
    private Set<Long> events;
    private Boolean pinned;
    private String title;
}
