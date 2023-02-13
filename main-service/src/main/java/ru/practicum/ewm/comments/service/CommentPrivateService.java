package ru.practicum.ewm.comments.service;

import ru.practicum.ewm.comments.dto.CommentFullDto;
import ru.practicum.ewm.comments.dto.NewUpdateCommentDto;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentPrivateService {
    CommentFullDto add(Long userId, Long eventId, NewUpdateCommentDto commentDto);

    CommentFullDto updateById(Long userId, Long commentId, NewUpdateCommentDto commentDto);

    void remove(Long userId, Long commentId);

    CommentFullDto getById(Long userId, Long commentId);

    List<CommentFullDto> getAll(Long userId, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean available, Integer from, Integer size);
}
