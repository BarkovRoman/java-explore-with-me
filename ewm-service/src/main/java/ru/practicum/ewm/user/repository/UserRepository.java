package ru.practicum.ewm.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.user.dto.ResponseUserDto;
import ru.practicum.ewm.user.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<ResponseUserDto> findUserByIdIn(List<Integer> ids);
}
