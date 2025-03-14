package com.example.springIntro.service.impl;

import com.example.springIntro.model.entity.Category;
import com.example.springIntro.repository.CategoryRepository;
import com.example.springIntro.service.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final String CATEGORY_PATH = "src/main/resources/files/categories.txt";
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories() throws IOException {
        if (categoryRepository.count() > 0) {
            return;
        }

        Files.readAllLines(Path.of(CATEGORY_PATH))
                .stream()
                .filter(line -> !line.isEmpty())
                .forEach(categoryName -> {
                    Category category = new Category(categoryName);

                    categoryRepository.save(category);
                });
    }

    @Override
    public Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();
        int randInt = ThreadLocalRandom.current().nextInt(1,3);
        long categoryRepoCount = categoryRepository.count();
        for (int i = 0; i < randInt; i++) {
            long randomId = ThreadLocalRandom.current().nextLong(1, categoryRepoCount);

            Category category = categoryRepository.findById(randomId).orElse(null);
            categories.add(category);
        }

        return categories;
    }
}
