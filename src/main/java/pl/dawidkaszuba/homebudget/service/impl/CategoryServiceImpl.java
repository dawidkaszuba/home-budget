package pl.dawidkaszuba.homebudget.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dawidkaszuba.homebudget.mapper.CategoryMapper;
import pl.dawidkaszuba.homebudget.model.db.BudgetUser;
import pl.dawidkaszuba.homebudget.model.db.Category;
import pl.dawidkaszuba.homebudget.model.db.CategoryType;
import pl.dawidkaszuba.homebudget.model.db.Home;
import pl.dawidkaszuba.homebudget.model.dto.category.CategoryViewDto;
import pl.dawidkaszuba.homebudget.model.dto.category.CreateCategoryDto;
import pl.dawidkaszuba.homebudget.model.dto.category.UpdateCategoryDto;
import pl.dawidkaszuba.homebudget.repository.CategoryRepository;
import pl.dawidkaszuba.homebudget.service.BudgetUserService;
import pl.dawidkaszuba.homebudget.service.CategoryService;
import pl.dawidkaszuba.homebudget.service.HomeService;

import java.security.Principal;
import java.util.List;
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
    public List<CategoryViewDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::mapToDto)
                .toList();
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
                dto.getName()
        )) {
            throw new IllegalStateException("Category already exists in this home");
        }

        Category category = categoryMapper.toEntity(dto);
        category.setHome(home);
        categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    @Override
    public Category getCategoryById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return optionalCategory.orElseThrow();
    }

    @Transactional
    @Override
    public void updateCategory(UpdateCategoryDto dto) {
        Category category = categoryRepository.findById(dto.getId()).orElseThrow();

        if (!category.getCategoryType().equals(dto.getCategoryType())) {
            category.setCategoryType(dto.getCategoryType());
        }
        if (!category.getName().equals(dto.getName())) {
            category.setName(dto.getName());
        }
    }

}
