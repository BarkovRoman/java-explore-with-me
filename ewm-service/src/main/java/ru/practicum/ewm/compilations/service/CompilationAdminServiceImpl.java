package ru.practicum.ewm.compilations.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.compilations.dto.CompilationDto;
import ru.practicum.ewm.compilations.dto.CompilationMapper;
import ru.practicum.ewm.compilations.dto.NewCompilationDto;
import ru.practicum.ewm.compilations.model.Compilation;
import ru.practicum.ewm.compilations.repository.CompilationRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.Set;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CompilationAdminServiceImpl implements CompilationAdminService {
    private final CompilationRepository repository;
    private final EventRepository eventRepository;
    private final CompilationMapper mapper;

    @Override
    @Transactional
    public CompilationDto create(NewCompilationDto newCompilationDto) {
        Set<Event> events = eventRepository.findByIdIn(newCompilationDto.getEvents());
        Compilation compilation = repository.save(mapper.toCompilation(newCompilationDto, events));
        log.info("Admin create Compilation={}", compilation);
        return mapper.toCompilationDto(compilation);
    }

    @Override
    @Transactional
    public void addEventById(Long eventId, Long compId) {
        Event event = isExistsEventById(eventId);
        Compilation compilation = isExistsCompilationById(compId);
        compilation.getEvents().add(event);
        log.info("Admin add eventId={}, compId={}", eventId, compId);
    }

    @Override
    @Transactional
    public void addCompIdByPin(Long compId) {
        Compilation compilation = isExistsCompilationById(compId);
        compilation.setPinned(true);
        log.info("Admin add pin compId={}", compId);
    }

    @Override
    @Transactional
    public void removeById(Long compId) {
        isExistsCompilationById(compId);
        repository.deleteById(compId);
        log.info("Admin delete compId={}", compId);
    }

    @Override
    @Transactional
    public void removeEventById(Long eventId, Long compId) {
        Event event = isExistsEventById(eventId);
        Compilation compilation = isExistsCompilationById(compId);
        compilation.getEvents().remove(event);
        log.info("Admin add eventId={}, compId={}", eventId, compId);
    }

    @Override
    @Transactional
    public void removeCompIdByPin(Long compId) {
        Compilation compilation = isExistsCompilationById(compId);
        compilation.setPinned(false);
        log.info("Admin delete pin compId={}", compId);
    }

    private Event isExistsEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event id=%s not found", eventId)));
    }

    private Compilation isExistsCompilationById(Long compId) {
        return repository.findById(compId)
                .orElseThrow(() -> new NotFoundException(String.format("Compilation id=%s not found", compId)));
    }
}
