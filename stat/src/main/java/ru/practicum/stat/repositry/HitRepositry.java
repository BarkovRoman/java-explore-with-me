package ru.practicum.stat.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.stat.dto.ResponseStatDto;
import ru.practicum.stat.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepositry extends JpaRepository<Hit, Long> {

    @Query(value = "select h.app, h.uri, count (distinct h.ip)" +
            "from Hit h " +
            "where h.uri = :uris and h.created between :start and :end " +
            "group by h.app, h.uri " +
            "order by COUNT(h.ip) desc ")
    List<ResponseStatDto> findByStatsByDistinct(@Param("start") LocalDateTime start,
                                                @Param("end") LocalDateTime end,
                                                @Param("uris") List<String> uris);

    @Query(value = "select new ru.practicum.stat.dto.ResponseStatDto(h.app, h.uri, COUNT(h.ip)) " +
            "from Hit h " +
            "where h.uri in (:uris) and h.created between :start and :end " +
            "group by h.app, h.uri " +
            "order by COUNT(h.ip) desc ")
    List<ResponseStatDto> findByStats(@Param("start") LocalDateTime start,
                                      @Param("end") LocalDateTime end,
                                      @Param("uris") List<String> uris);
}