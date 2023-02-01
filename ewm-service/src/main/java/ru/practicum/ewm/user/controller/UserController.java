package ru.practicum.ewm.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.user.dto.NewUserDto;
import ru.practicum.ewm.user.dto.ResponseUserDto;
import ru.practicum.ewm.user.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseUserDto add(@Valid @RequestBody NewUserDto newUserDto) {
        log.info("Crete User={}", newUserDto);
        return userService.add(newUserDto);
    }

    @GetMapping("/{id}")
    public ResponseUserDto getById(@PathVariable Long id) {
        log.info("Get User id={}", id);
        return userService.getById(id);
    }

    @GetMapping
    public List<ResponseUserDto> getAll(@PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                        @Positive @RequestParam(name = "size", defaultValue = "10") Integer size,
                                        @RequestParam(required = false) List<Integer> ids) {
        log.info("Get User all RequestParam from={}, size={}, ids={}", from, size, ids);
        return userService.getAll(from, size, ids);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        log.info("Delete User id={}", id);
        userService.remove(id);
    }
}
