package ru.practicum.ewm.comments.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.comments.model.Comment;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c " +
            "where c.author.id = ?1 and c.available = ?2 " +
            "and c.created between ?3 and ?4 " +
            "order by c.created")
    List<Comment> findCommitAndUserIdAndAvailable(Long id, boolean available, LocalDateTime start, LocalDateTime end, PageRequest page);

    boolean existsByIdAndAuthor_IdAndAvailableFalse(Long commentId, Long userId);

    Comment findByIdAndAuthor_Id(Long commentId, Long userId);

    @Query("select c from Comment c " +
            "where c.author.id = ?1 " +
            "and c.created between ?2 and ?3 " +
            "order by c.created")
    List<Comment> findCommitAndUserId(Long userId, LocalDateTime rangeStart, LocalDateTime rangeEnd, PageRequest page);
}
