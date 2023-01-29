package ru.practicum.stat.repositry;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.stat.model.Hit;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class HitRepositryTest {
    private final HitRepositry repositry;
    private Hit hit;

    @BeforeEach
    public void setUp() {
        LocalDateTime created = LocalDateTime.now();
        hit = new Hit(1L, "ewm-main-service", "/events/1", "192.163.0.1", created);
    }

    @Test
    public void addHit() {
        Hit hitTest = repositry.save(hit);

        assertThat(hitTest).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(hitTest).hasFieldOrPropertyWithValue("app", "ewm-main-service");
        assertThat(hitTest).hasFieldOrPropertyWithValue("uri", "/events/1");
    }
}