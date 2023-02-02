package ru.practicum.ewm.event.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.categories.model.Category;
import ru.practicum.ewm.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "event", schema = "public")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(nullable = false)
    private String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    private Integer confirmedRequests;
    private LocalDateTime createdOn;
    private String description;
    @Column(nullable = false)
    private LocalDateTime eventDate;
    @ManyToOne
    @JoinColumn(name = "initiator_id", nullable = false)
    private User initiator;
    @Transient
    @Convert(converter = Location.class)
    private Location location;
    @Column(nullable = false)
    private Boolean paid;
    private Integer participantLimit;
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    @Enumerated(EnumType.STRING)
    private State state;
    @Column(nullable = false)
    private String title;
    private Integer views;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        return id != null && id.equals(((Event) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
