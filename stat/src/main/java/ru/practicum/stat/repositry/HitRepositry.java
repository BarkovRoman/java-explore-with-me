package ru.practicum.stat.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.stat.model.Hit;

public interface HitRepositry extends JpaRepository<Hit, Long> {
}
