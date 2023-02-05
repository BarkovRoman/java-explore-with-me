package ru.practicum.ewm.event.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
@Embeddable
public class Location {
    private Float lat;
    private Float lon;
}
