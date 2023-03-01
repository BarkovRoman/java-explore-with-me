package ru.practicum.ewm.event.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.State;

import java.util.List;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryCustom, QuerydslPredicateExecutor<Event> {

    boolean existsByCategory_Id(Long id);

    List<Event> findEventByInitiatorId(Long userId, PageRequest page);

    boolean existsEventByIdAndInitiatorId(Long eventId, Long userId);

    Event getByIdAndState(Long id, State state);

    Set<Event> findByIdIn(Set<Long> events);
}