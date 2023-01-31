package ru.practicum.ewm.categories.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    @NotNull
    private Long id;
    @NotBlank(message = "Name - не заполнен")
    private String name;
}
