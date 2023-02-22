package ru.practicum.ewm.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.model.RequestStatus;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    Long countByEventAndStatus(Long event, RequestStatus status);

    boolean existsByEventAndRequesterAndStatus(Long event, Long requester, RequestStatus state);

    boolean existsByRequesterAndEvent(Long requester, Long event);

    List<Request> findByEvent(Long event);

    List<Request> findRequestsByRequester(Long userId);

    List<Request> findRequestsByIdIn(List<Long> requestIds);

    List<Request> findByEventInAndStatus(List<Long> eventIds, RequestStatus status);
}
