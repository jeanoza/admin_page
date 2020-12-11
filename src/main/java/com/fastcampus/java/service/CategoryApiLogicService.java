package com.fastcampus.java.service;

import com.fastcampus.java.controller.ifs.CrudInterface;
import com.fastcampus.java.model.entity.Category;
import com.fastcampus.java.model.network.Header;
import com.fastcampus.java.model.network.request.CategoryApiRequest;
import com.fastcampus.java.model.network.response.CategoryApiResponse;
import com.fastcampus.java.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryApiLogicService implements CrudInterface<CategoryApiRequest, CategoryApiResponse> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Header<CategoryApiResponse> create(Header<CategoryApiRequest> request) {
        CategoryApiRequest body = request.getData();
        Category category = Category.builder()
                .type(body.getType())
                .title(body.getTitle())
                .build();
        Category newCategory = categoryRepository.save(category);

        return response(newCategory);
    }

    @Override
    public Header<CategoryApiResponse> read(Long id) {
        return categoryRepository.findById(id)
                .map(this::response)
                .orElseGet(() -> Header.ERROR("NO DATA"));
    }

    @Override
    public Header<CategoryApiResponse> update(Header<CategoryApiRequest> request) {
        CategoryApiRequest body = request.getData();
        return categoryRepository.findById(body.getId())
                .map(category -> category
                        .setTitle(body.getTitle())
                        .setType(body.getType())
                )
                .map(category -> categoryRepository.save(category))
                .map(this::response)
                .orElseGet(() -> Header.ERROR("NO DATA"));
    }

    @Override
    public Header delete(Long id) {
        return categoryRepository.findById(id)
                .map(category -> {
                    categoryRepository.delete(category);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("NO DATA"));
    }

    private Header<CategoryApiResponse> response(Category category) {
        CategoryApiResponse body = CategoryApiResponse.builder()
                .id(category.getId())
                .title(category.getTitle())
                .type(category.getType())
                .build();
        return Header.OK(body);
    }
}
