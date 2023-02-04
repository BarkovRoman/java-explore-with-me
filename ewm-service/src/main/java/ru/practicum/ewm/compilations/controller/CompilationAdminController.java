package ru.practicum.ewm.compilations.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilations.dto.CompilationDto;
import ru.practicum.ewm.compilations.dto.NewCompilationDto;
import ru.practicum.ewm.compilations.service.CompilationAdminService;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/compilations")
public class CompilationAdminController {
    private final CompilationAdminService compilationAdminService;

    @PostMapping
    public CompilationDto create(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        log.info("Admin create Compilation={}", newCompilationDto);
        return compilationAdminService.create(newCompilationDto);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEventById(@PathVariable Long compId,
                             @PathVariable Long eventId) {
        log.info("Admin add eventId={}, compId={}", eventId, compId);
        compilationAdminService.addEventById(eventId, compId);
    }

    @PatchMapping("/{compId}/pin")
    public void addCompIdByPin(@PathVariable Long compId) {
        log.info("Admin add pin compId={}", compId);
        compilationAdminService.addCompIdByPin(compId);
    }

    @DeleteMapping("/{compId}")
    public void removeById(@PathVariable Long compId) {
        log.info("Admin delete compId={}", compId);
        compilationAdminService.removeById(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void removeEventById(@PathVariable Long compId,
                             @PathVariable Long eventId) {
        log.info("Admin delete eventId={}, compId={}", eventId, compId);
        compilationAdminService.removeEventById(eventId, compId);
    }

    @DeleteMapping("/{compId}/pin")
    public void removeCompIdByPin(@PathVariable Long compId) {
        log.info("Admin delete pin compId={}", compId);
        compilationAdminService.removeCompIdByPin(compId);
    }





}
