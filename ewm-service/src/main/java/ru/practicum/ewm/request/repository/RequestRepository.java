package ru.practicum.ewm.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.request.model.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    boolean existsRequestByEventAndRequester(Long userId, Long eventId);

    List<Request> findRequestsByRequester(Long userId);
}
