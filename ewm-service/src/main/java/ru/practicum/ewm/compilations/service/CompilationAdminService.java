package ru.practicum.ewm.compilations.service;

import ru.practicum.ewm.compilations.dto.CompilationDto;
import ru.practicum.ewm.compilations.dto.NewCompilationDto;
import ru.practicum.ewm.compilations.dto.UpdateCompilationRecuest;

public interface CompilationAdminService {
    CompilationDto create(NewCompilationDto newCompilationDto);

    void addEventById(Long eventId, Long compId);

    CompilationDto updateByPin(Long compId, UpdateCompilationRecuest updateCompilationRecuest);

    void removeById(Long compId);

    void removeEventById(Long eventId, Long compId);

    void removeCompIdByPin(Long compId);
}
