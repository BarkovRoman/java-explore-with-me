package ru.practicum.ewm.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.event.dto.EventShort;
import ru.practicum.ewm.event.model.Event;
;import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<EventShort> findEventByInitiatorId(Long userId, PageRequest page);

    Event findByIdAndInitiatorId(Long userId, Long eventId);

    boolean existsEventByIdAndInitiatorId(Long eventId, Long userId);
}
