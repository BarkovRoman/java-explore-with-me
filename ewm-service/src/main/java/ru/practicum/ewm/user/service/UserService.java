package ru.practicum.ewm.user.service;

import ru.practicum.ewm.user.dto.CreateUser;
import ru.practicum.ewm.user.dto.ResponseUserDto;

import java.util.List;

public interface UserService {

    ResponseUserDto add(CreateUser createUser);

    ResponseUserDto getById(Long userId);

    List<ResponseUserDto> getAll(Integer from, Integer size, List<Integer> ids);

    void remove(Long userId);
}
