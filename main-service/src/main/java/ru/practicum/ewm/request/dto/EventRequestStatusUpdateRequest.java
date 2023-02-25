package ru.practicum.ewm.request.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.request.model.RequestStatus;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestStatusUpdateRequest {
    private List<Long> requestIds;
    private RequestStatus status;
}
