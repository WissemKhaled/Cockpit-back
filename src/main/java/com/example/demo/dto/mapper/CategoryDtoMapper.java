package com.example.demo.dto.mapper;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.CreateGstLogDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.GstLog;

public class CategoryDtoMapper {

	public CategoryDTO toDto(Category category) {
		return new CategoryDTO(category.getCatId(), category.getCatName());
	}

	public Category toCategory(CategoryDTO categoryDTO) {
		return new Category(categoryDTO.getCatId(), categoryDTO.getCatName());
	}

}
