package ru.practicum.ewm.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUserDto {
    private Long id;
    private String name;
    private String email;
}
