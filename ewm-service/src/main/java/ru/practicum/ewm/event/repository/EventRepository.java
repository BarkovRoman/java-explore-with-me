package ru.practicum.ewm.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.events.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
