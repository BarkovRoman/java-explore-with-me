package ru.practicum.ewm.compilations.service;

import ru.practicum.ewm.compilations.dto.CompilationDto;
import ru.practicum.ewm.compilations.dto.NewCompilationDto;

public interface CompilationAdminService {
    CompilationDto create(NewCompilationDto newCompilationDto);

    void addEventById(Long eventId, Long compId);

    void addCompIdByPin(Long compId);

    void removeById(Long compId);

    void removeEventById(Long eventId, Long compId);

    void removeCompIdByPin(Long compId);
}
