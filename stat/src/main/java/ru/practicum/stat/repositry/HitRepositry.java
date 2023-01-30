package ru.practicum.stat.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.stat.dto.ResponseStatDto;
import ru.practicum.stat.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepositry extends JpaRepository<Hit, Long> {

    @Query(value = "SELECT new ru.practicum.stat.dto.ResponseStatDto(h.app, h.uri, COUNT(DISTINCT h.ip)) " +
            "FROM Hit h " +
            "WHERE ((:uris) IS NULL OR h.uri IN (:uris)) AND h.created BETWEEN :start AND :end " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.ip) DESC ")
    List<ResponseStatDto> statByUniqueIp(@Param("start") LocalDateTime start,
                                         @Param("end") LocalDateTime end,
                                         @Param("uris") List<String> uris);

    @Query(value = "SELECT new ru.practicum.stat.dto.ResponseStatDto(h.app, h.uri, COUNT(h.ip)) " +
            "FROM Hit h " +
            "WHERE h.uri IN (:uris) AND h.created BETWEEN :start AND :end " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.ip) DESC ")
    List<ResponseStatDto> statByIp(@Param("start") LocalDateTime start,
                                   @Param("end") LocalDateTime end,
                                   @Param("uris") List<String> uris);
}