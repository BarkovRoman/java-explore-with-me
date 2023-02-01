package ru.practicum.ewm.categories.service;

import ru.practicum.ewm.categories.dto.CategoryDto;
import ru.practicum.ewm.categories.dto.NewCategoryDto;
import ru.practicum.ewm.categories.dto.ResponseCategoryDto;

import java.util.List;

public interface CategoryService {
    ResponseCategoryDto add(NewCategoryDto newCategoryDto);

    ResponseCategoryDto update(CategoryDto categoryDto);

    void remove(Long id);

    List<ResponseCategoryDto> getAll(Integer from, Integer size);

    ResponseCategoryDto getById(Long id);
}
