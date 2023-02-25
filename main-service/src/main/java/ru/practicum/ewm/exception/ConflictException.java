package ru.practicum.ewm.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ConflictException extends RuntimeException  {
    private final String message;
}
