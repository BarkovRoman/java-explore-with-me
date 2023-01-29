package ru.practicum.stat.service;

import lombok.RequiredArgsConstructor;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stat.dto.CreateHitDto;
import ru.practicum.stat.dto.HitMapper;
import ru.practicum.stat.dto.ResponseHitDto;
import ru.practicum.stat.dto.ResponseStatDto;
import ru.practicum.stat.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Transactional
@SpringBootTest(
        properties = "db.name=test",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)

public class HitServiceTest {
    private final HitService hitService;
    private final HitMapper mapper;

    @Test
    public void createHit() {
        CreateHitDto createHitDto = new CreateHitDto("ewm-main-service", "/events/1", "192.163.0.1");

        ResponseHitDto hit = hitService.create(createHitDto);

        MatcherAssert.assertThat("ewm-main-service", equalTo(hit.getApp()));
        MatcherAssert.assertThat("/events/1", equalTo(hit.getUri()));

    }

    @Test
    @Sql("/schemaTest.sql")
    public void getStatNoDistinct() {
        List<String> uris = List.of("/events/1", "/events/2");

        hitService.create(new CreateHitDto("ewm-main-service", "/events/1", "192.163.0.1"));
        hitService.create(new CreateHitDto("ewm-main-service", "/events/2", "192.163.0.2"));
        hitService.create(new CreateHitDto("ewm-main-service", "/events/2", "192.163.0.2"));

        List<ResponseStatDto> hits = hitService.get("2020-05-05 00:00:00", "2035-05-05 00:00:00",false, uris);

        MatcherAssert.assertThat(2, equalTo(hits.size()));
        MatcherAssert.assertThat(2L, equalTo(hits.get(0).getHits()));
    }

    @Test
    @Sql("/schemaTest.sql")
    public void getStatDistinct() {
        List<String> uris = List.of("/events/1", "/events/2");

        hitService.create(new CreateHitDto("ewm-main-service", "/events/1", "192.163.0.1"));
        hitService.create(new CreateHitDto("ewm-main-service", "/events/2", "192.163.0.2"));
        hitService.create(new CreateHitDto("ewm-main-service", "/events/2", "192.163.0.2"));

        List<ResponseStatDto> hits = hitService.get("2020-05-05 00:00:00", "2035-05-05 00:00:00",true, uris);

        MatcherAssert.assertThat(2, equalTo(hits.size()));
        MatcherAssert.assertThat(1L, equalTo(hits.get(0).getHits()));
    }

    @Test
    public void getStatStartEnd() {
        List<String> uris = List.of("/events/1", "/events/2");
        assertThatThrownBy(() -> {
            List<ResponseStatDto> hits = hitService.get("2045-05-05 00:00:00", "2035-05-05 00:00:00",true, uris);
        }).isInstanceOf(Throwable.class);
    }

    @Test
    public void responseHitDtoTest() {
        LocalDateTime created = LocalDateTime.now();
        Hit hit = new Hit(1L, "ewm-main-service", "/events/1", "192.163.0.1", created);
        ResponseHitDto responseHitDto = new ResponseHitDto(1L, "ewm-main-service", "/events/1", "192.163.0.1", "created");

        ResponseHitDto test = mapper.toResponseHitDto(hit);

        assertThat(responseHitDto, equalTo(test));
        assertThat(responseHitDto.hashCode(), equalTo(test.hashCode()));
    }

    @Test
    @Sql("/schemaTest.sql")
    public void hitTest() {
        LocalDateTime created = LocalDateTime.now();
        Hit hit = new Hit(1L, "ewm-main-service", "/events/1", "192.163.0.1", created);
        CreateHitDto createHitDto = new CreateHitDto("ewm-main-service", "/events/1", "192.163.0.1");

        Hit test = mapper.toHit(createHitDto);

        assertThat(hit.getApp(), equalTo(test.getApp()));
        assertThat(hit.hashCode(), equalTo(test.hashCode()));
    }
}
