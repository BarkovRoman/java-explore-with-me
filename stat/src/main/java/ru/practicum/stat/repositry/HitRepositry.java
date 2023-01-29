package ru.practicum.stat.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.stat.dto.ResponseStatDto;
import ru.practicum.stat.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepositry extends JpaRepository<Hit, Long> {

    @Query(value = "select new ru.practicum.stat.dto.ResponseStatDto(h.app, h.uri, COUNT(DISTINCT h.ip)) " +
            "from Hit h " +
            "where ((:uris) IS NULL OR h.uri IN (:uris)) and h.created between :start and :end " +
            "group by h.app, h.uri " +
            "order by COUNT(h.ip) desc ")
    List<ResponseStatDto> statByUniqueIp(@Param("start") LocalDateTime start,
                                         @Param("end") LocalDateTime end,
                                         @Param("uris") List<String> uris);

    @Query(value = "select new ru.practicum.stat.dto.ResponseStatDto(h.app, h.uri, COUNT(h.ip)) " +
            "from Hit h " +
            "where h.uri in (:uris) and h.created between :start and :end " +
            "group by h.app, h.uri " +
            "order by COUNT(h.ip) desc ")
    List<ResponseStatDto> statByIp(@Param("start") LocalDateTime start,
                                   @Param("end") LocalDateTime end,
                                   @Param("uris") List<String> uris);
}