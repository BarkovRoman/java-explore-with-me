package ru.practicum.stat.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class IllegalRequestException extends RuntimeException {
    private final String error;
}
