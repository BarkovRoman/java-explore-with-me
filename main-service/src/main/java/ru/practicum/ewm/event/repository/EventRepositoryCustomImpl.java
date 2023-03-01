package ru.practicum.ewm.event.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.QEvent;
import ru.practicum.ewm.event.model.State;
import ru.practicum.ewm.request.model.QRequest;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventRepositoryCustomImpl implements EventRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<Event> findEventByFilterDate(String text, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, Integer from, Integer size, List<Long> categories) {
        QEvent event = QEvent.event;
        QRequest request = QRequest.request;
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        BooleanBuilder filter = publicFilter(text, paid, rangeStart, rangeEnd, onlyAvailable, categories, event, request);

        return queryFactory
                .selectFrom(event)
                .innerJoin(event.category).fetchJoin()
                .innerJoin(event.initiator).fetchJoin()
                .where(filter)
                .orderBy(event.eventDate.asc())
                .offset(from)
                .limit(size)
                .fetch();
    }

    @Override
    public List<Event> findEventByFilterViews(String text, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, List<Long> categories) {
        QEvent event = QEvent.event;
        QRequest request = QRequest.request;
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        BooleanBuilder filter = publicFilter(text, paid, rangeStart, rangeEnd, onlyAvailable, categories, event, request);

        return queryFactory
                .selectFrom(event)
                .innerJoin(event.category).fetchJoin()
                .innerJoin(event.initiator).fetchJoin()
                .where(filter)
                .fetch();
    }

    private BooleanBuilder publicFilter(String text, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, List<Long> categories, QEvent event, QRequest request) {
        rangeStart = rangeStart == null ? LocalDateTime.now() : rangeStart;
        rangeEnd = rangeEnd == null ? rangeStart.plusWeeks(1) : rangeEnd;

        BooleanBuilder filter = new BooleanBuilder(
                event.eventDate.between(rangeStart, rangeEnd)
                        .and(event.state.eq(State.PUBLISHED)));

        if (text != null) {
            filter.and(event.annotation.likeIgnoreCase(text)).or(event.description.likeIgnoreCase(text));
        }
        if (paid != null) {
            filter.and(event.paid.eq(paid));
        }
        if (categories != null && !categories.isEmpty()) {
            filter.and(event.category.id.in(categories));
        }
        if (onlyAvailable) {
            JPQLQuery<Long> eventIds = JPAExpressions
                    .select(event.id)
                    .from(event)
                    .leftJoin(request).on(request.event.eq(event.id))
                    .where(filter)
                    .groupBy(event)
                    .having(request.count().lt(event.participantLimit).or(event.participantLimit.eq(0L)));
            filter = new BooleanBuilder(event.id.in(eventIds));
        }
        return filter;
    }
}
