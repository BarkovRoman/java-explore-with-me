package ru.practicum.stat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseHitDto {
    private Long id;
    private String app;
    private String uri;
    private String ip;
    private String created;
}
