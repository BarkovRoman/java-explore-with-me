package ru.practicum.ewm.compilations.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.event.service.EventService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class CompilationPrivateController {
    private final EventService eventService;



}
