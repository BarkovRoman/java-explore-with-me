package ru.practicum.ewm.compilations.service;

import ru.practicum.ewm.compilations.dto.CompilationDto;
import ru.practicum.ewm.compilations.dto.NewCompilationDto;
import ru.practicum.ewm.compilations.dto.UpdateCompilationRecuest;

public interface CompilationAdminService {
    CompilationDto create(NewCompilationDto newCompilationDto);

    CompilationDto updateByPin(Long compId, UpdateCompilationRecuest updateCompilationRecuest);

    void removeById(Long compId);
}
