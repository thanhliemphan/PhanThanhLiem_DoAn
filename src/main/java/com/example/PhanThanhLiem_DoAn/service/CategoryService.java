package com.example.PhanThanhLiem_DoAn.service;

import com.example.PhanThanhLiem_DoAn.dto.CategoryDto;
import com.example.PhanThanhLiem_DoAn.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category save(Category category);
    Category update(Category category);
    void deleteById(Long id);
    void enableById(Long id);
    Category findById(Long id);
    List<Category> findAllByActivatedTrue();

    /*customer*/
    List<CategoryDto> getCategoryAndProduct();
}
