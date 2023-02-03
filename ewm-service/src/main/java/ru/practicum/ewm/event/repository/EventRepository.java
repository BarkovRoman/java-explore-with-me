package ru.practicum.ewm.event.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import ru.practicum.ewm.event.dto.EventShort;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.State;
;import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByInitiator_Id(@Nullable Long id);
    List<Event> findByInitiator_IdAndStateAndCategory_IdAndEventDateBetween(Long id, State state, Long id1, LocalDateTime eventDateStart, LocalDateTime eventDateEnd);

    List<EventShort> findEventByInitiatorId(Long userId, PageRequest page);

    boolean existsEventByIdAndInitiatorId(Long eventId, Long userId);

    @Query("SELECT e FROM Event e " +
            "WHERE  upper(e.annotation) like upper(concat('%', ?1, '%')) " +
            "or upper(e.title) like upper(concat('%', ?1, '%')) " +
            "AND e.paid = ?2 AND e.eventDate BETWEEN ?3 AND ?4 " +
            "AND e.category.id = ?5 AND e.state = 'PUBLISHED' " +
            "AND e.confirmedRequests < e.participantLimit OR e.confirmedRequests = 0" +
            "ORDER BY e.views DESC "
    )
    List<EventShort> findEventSortViews(String text, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, List<Long> categories, PageRequest page);

    @Query("SELECT e FROM Event e " +
            "WHERE  upper(e.annotation) like upper(concat('%', ?1, '%')) " +
            "or upper(e.title) like upper(concat('%', ?1, '%')) " +
            "AND e.paid = ?2 AND e.eventDate BETWEEN ?3 AND ?4 " +
            "AND e.category.id = ?5 AND e.state = 'PUBLISHED' " +
            "ORDER BY e.eventDate DESC "
    )
    List<EventShort> findEventSortDate(String text, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, List<Long> categories, PageRequest page);

    @Query("SELECT e FROM Event e " +
            "WHERE  upper(e.annotation) like upper(concat('%', ?1, '%')) " +
            "or upper(e.title) like upper(concat('%', ?1, '%')) " +
            "AND e.paid = ?2 AND e.eventDate BETWEEN ?3 AND ?4 " +
            "AND e.category.id = ?5 AND e.state = 'PUBLISHED' " +
            "AND e.confirmedRequests < e.participantLimit OR e.confirmedRequests = 0" +
            "ORDER BY e.views DESC "
    )
    List<EventShort> findEventByAvailableSortViews(String text, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, List<Long> categories, PageRequest page);

    @Query("SELECT e FROM Event e " +
            "WHERE  upper(e.annotation) like upper(concat('%', ?1, '%')) " +
            "or upper(e.title) like upper(concat('%', ?1, '%')) " +
            "AND e.paid = ?2 AND e.eventDate BETWEEN ?3 AND ?4 " +
            "AND e.category.id = ?5 AND e.state = 'PUBLISHED' " +
            "AND e.confirmedRequests < e.participantLimit OR e.confirmedRequests = 0" +
            "ORDER BY e.eventDate DESC "
    )
    List<EventShort> findEventByAvailableSortDate(String text, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, List<Long> categories, PageRequest page);

    Event getByIdAndState(Long id, State state);

    @Query("SELECT e FROM Event e " +
            "WHERE e.initiator.id = ?1 AND e.state = ?2 AND e.category.id = ?3 " +
            "AND e.eventDate BETWEEN ?4 AND ?5")
    List<Event> findEventByInitiatorIdAndStateAndCategory_IdAndEventDateBetween(List<Long> users, List<String> states, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, PageRequest page);
}
