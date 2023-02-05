package ru.practicum.ewm.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.event.model.State;
import ru.practicum.ewm.request.model.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findByEvent(Long event);

    List<Request> findByIdAndEventAndStatus(Long id, Long event, State status);

    boolean existsRequestByEventAndRequester(Long userId, Long eventId);

    List<Request> findRequestsByRequester(Long userId);

    Request findByIdAndEvent(Long reqId, Long eventId);

    List<Request> findRequestsByEventIn(List<Long> RequestIds);
}
