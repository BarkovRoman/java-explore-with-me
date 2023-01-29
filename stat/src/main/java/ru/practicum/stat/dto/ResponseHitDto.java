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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResponseHitDto)) return false;
        return id != null && id.equals(((ResponseHitDto) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
