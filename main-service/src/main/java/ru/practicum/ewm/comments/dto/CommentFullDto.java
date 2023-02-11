package ru.practicum.ewm.comments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentFullDto {
    private Long id;
    private String text;
    private boolean available;
    private LocalDateTime created;
    private Long event;
}
