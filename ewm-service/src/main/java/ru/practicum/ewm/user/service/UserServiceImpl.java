package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.user.dto.CreateUser;
import ru.practicum.ewm.user.dto.ResponseUserDto;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.util.List;

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
        User user = userRepository.save(mapper.toUser(createUser, 0L));
        log.debug("Add User DB user={}", user);
        return mapper.toResponseUserDto(user);
    }

    @Override
    public ResponseUserDto getById(Long userId) {
        return null;
    }

    @Override
    public List<ResponseUserDto> getAll(Integer from, Integer size, List<Integer> ids) {
        return null;
    }

    @Override
    @Transactional
    public void remove(Long userId) {
        isExistsUserById(id);
        userRepository.deleteById(id);
        log.debug("Delete User id={}", id);

    }

    private void isExistsUserById(long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User id=%s not found", id)));
    }
}
