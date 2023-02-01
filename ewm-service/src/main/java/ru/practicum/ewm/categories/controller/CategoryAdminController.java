package ru.practicum.ewm.categories.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.categories.dto.CategoryDto;
import ru.practicum.ewm.categories.dto.NewCategoryDto;
import ru.practicum.ewm.categories.dto.ResponseCategoryDto;
import ru.practicum.ewm.categories.service.CategoryService;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/categories")
public class CategoryAdminController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseCategoryDto add(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        log.info("Crete Category={}", newCategoryDto);
        return categoryService.add(newCategoryDto);
    }

    @PatchMapping
    public ResponseCategoryDto update(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("Update Category={}", categoryDto);
        return categoryService.update(categoryDto);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        log.info("Remove CategoryID={}", id);
        categoryService.remove(id);
    }
}
