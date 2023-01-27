package ru.practicum.stat.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.stat.dto.ResponseStatDto;
import ru.practicum.stat.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepositry extends JpaRepository<Hit, Long> {

    @Query("select h, count (distinct h.ip) from Hit h where h.uri = ?3 and h.created between ?1 and ?2 group by h.ip")
    ResponseStatDto findByStatsByDistinct(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("select h, count  h.ip) from Hit h where h.uri = ?3 and h.created between ?1 and ?2 group by h.ip")
    ResponseStatDto findByStats(LocalDateTime start, LocalDateTime end, List<String> uris);
}