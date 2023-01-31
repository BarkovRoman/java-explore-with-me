package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.user.dto.CreateUser;
import ru.practicum.ewm.user.dto.ResponseUserDto;
import ru.practicum.ewm.user.dto.UserMapper;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    @Transactional
    public ResponseUserDto add(CreateUser createUser) {
        User user = userRepository.save(mapper.toUser(createUser));
        log.debug("Add User DB user={}", user);
        return mapper.toResponseUserDto(user);
    }

    @Override
    public ResponseUserDto getById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User id=%s not found", userId)));
        log.info("user found {}", user);
        return mapper.toResponseUserDto(user);
    }

    @Override
    public List<ResponseUserDto> getAll(Integer from, Integer size, List<Integer> ids) {
        if (!ids.isEmpty()) {
            return userRepository.findUserByIdIn(ids);
        }
        final PageRequest page = PageRequest.of(from, size);
        Page<User> users = userRepository.findAll(page);
        return users.stream().map(mapper::toResponseUserDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void remove(Long userId) {
        isExistsUserById(userId);
        userRepository.deleteById(userId);
        log.info("Delete User id={}", userId);

    }

    private void isExistsUserById(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User id=%s not found", id)));
    }
}
