package ru.practicum.ewm.exception.validation;

import ru.practicum.ewm.event.dto.NewEventDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CheckDateValidator implements ConstraintValidator<CreatedValid, NewEventDto> {
    @Override
    public void initialize(CreatedValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(NewEventDto eventDto, ConstraintValidatorContext constraintValidatorContext) {
        LocalDateTime dateTime = LocalDateTime.now().plusHours(2);

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime created = LocalDateTime.parse(eventDto.getEventDate(), format);

        return created.isBefore(dateTime);
    }
}