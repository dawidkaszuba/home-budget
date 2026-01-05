package pl.dawidkaszuba.homebudget.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import pl.dawidkaszuba.homebudget.exceptions.CategoryAlreadyExistsException;
import pl.dawidkaszuba.homebudget.exceptions.CategoryNotFoundException;
import pl.dawidkaszuba.homebudget.mapper.CategoryMapper;
import pl.dawidkaszuba.homebudget.model.db.Category;
import pl.dawidkaszuba.homebudget.model.db.CategoryType;
import pl.dawidkaszuba.homebudget.model.db.Home;
import pl.dawidkaszuba.homebudget.model.dto.category.CreateCategoryDto;
import pl.dawidkaszuba.homebudget.model.dto.category.UpdateCategoryDto;
import pl.dawidkaszuba.homebudget.repository.CategoryRepository;
import pl.dawidkaszuba.homebudget.service.CategoryService;
import pl.dawidkaszuba.homebudget.service.HomeService;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final HomeService homeService;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, HomeService homeService, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.homeService = homeService;
        this.categoryMapper = categoryMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Category> getAllCategories(Principal principal) {
        Home home = homeService.getHomeByBudgetUser(principal.getName());
        return categoryRepository.findAllByHome(home);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Category> findByCategoryType(CategoryType type) {
        return categoryRepository.findByCategoryType(type);
    }

    @Transactional
    @Override
    public void save(CreateCategoryDto dto, Principal principal) {
        Home home = homeService.getHomeByBudgetUser(principal.getName());

        if (categoryRepository.existsByHomeAndCategoryTypeAndName(
                home,
                dto.getCategoryType(),
                dto.getName())) {
            throw new CategoryAlreadyExistsException("Category already exists in this home");
        }

        Category category = categoryMapper.toEntity(dto);
        category.setHome(home);
        categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    @Override
    public Category getCategoryById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return optionalCategory
                .orElseThrow(() -> new CategoryNotFoundException("Category with id: " + id + " does not exists."));
    }

    @Transactional
    @Override
    public void updateCategory(UpdateCategoryDto dto) {
        Category category = categoryRepository.findById(dto.getId())
                .orElseThrow(() -> new CategoryNotFoundException("Category with id: " + dto.getId() + " does not exists."));

        if (!category.getCategoryType().equals(dto.getCategoryType())) {
            category.setCategoryType(dto.getCategoryType());
        }
        if (!category.getName().equals(dto.getName())) {
            category.setName(dto.getName());
        }

        String note = StringUtils.hasText(dto.getNote()) ? dto.getNote() : null;
        if (!Objects.equals(note, category.getNote())) {
            category.setNote(note);
        }
    }

}
