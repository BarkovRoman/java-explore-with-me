package ru.practicum.ewm.categories.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.categories.dto.CategoryDto;
import ru.practicum.ewm.categories.dto.CategoryMapper;
import ru.practicum.ewm.categories.dto.NewCategoryDto;
import ru.practicum.ewm.categories.dto.ResponseCategoryDto;
import ru.practicum.ewm.categories.model.Category;
import ru.practicum.ewm.categories.repository.CategoryRepository;
import ru.practicum.ewm.exception.NotFoundException;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public ResponseCategoryDto add(NewCategoryDto newCategoryDto) {
        Category category = categoryRepository.save(categoryMapper.toCategory(newCategoryDto));
        log.info("Add Category={}", category);
        return categoryMapper.toResponseCategoryDto(category);
    }

    @Override
    @Transactional
    public ResponseCategoryDto update(CategoryDto categoryDto) {
        isExistsCategoryById(categoryDto.getId());

        Category category = categoryRepository.findById(categoryDto.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Category id=%s not found", categoryDto.getId())));
        category.setName(categoryDto.getName());
        log.info("Update Category={}", category);
        return categoryMapper.toResponseCategoryDto(category);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        isExistsCategoryById(id);
        // Обратите внимание: с категорией не должно быть связано ни одного события.
        log.info("Delete CategoryId={}", id);
    }

    private void isExistsCategoryById(Long id) {
        categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Category id=%s not found", id)));
    }
}
