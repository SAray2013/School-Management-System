package com.tday.school_management_system.service;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atMostOnce;

import org.mockito.Mock;
import org.mockito.Captor;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.quality.Strictness;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.junit.jupiter.MockitoSettings;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


import com.tday.school_management_system.utils.PageUtil;
import com.tday.school_management_system.model.Category;
import com.tday.school_management_system.spec.CategorySpec;
import com.tday.school_management_system.spec.CategoryFilter;
import com.tday.school_management_system.exception.ApiException;
import com.tday.school_management_system.repository.CategoryRepository;
import com.tday.school_management_system.service.impl.CategoryServiceImpl;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CategoryServiceTest {
	
	@Mock
	private CategoryRepository categoryRepository;
	@Captor
	private ArgumentCaptor<Category> categoryCapture;
	private CategoryService categoryService;
	private Category category;
	
	@BeforeEach
	public void setup() {
		categoryService = new CategoryServiceImpl(categoryRepository);
		category = new Category();
		category.setCategoryId(1L);
		category.setCategoryName("Java");
		category.setIsDeleted((short) 1);
		when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
	}
	
	@Test
	public void testCreateCategory() {
		// Given
		category = new Category();
		category.setCategoryId(1L);
		category.setCategoryName("Java");
		category.setIsDeleted((short) 1);
		// When
		Category ResponseCategory = categoryService.create(category);
		// Then
		verify(categoryRepository, times(1)).save(category);
	}
	
	@Test
	public void testGetById() {
		// Given
		// ...
		// When
		Category ResponseCategory = categoryService.getById(1L);
		// Then
		assertNotNull(ResponseCategory);
		assertEquals("Java", ResponseCategory.getCategoryName());
		assertEquals(1, ResponseCategory.getCategoryId());
	}
	
	@Test
	public void getByIdThrowException() {
		// Given
		// ...
		// When
		when(categoryRepository.findById(2L)).thenReturn(Optional.empty());
		// Then
		assertThatThrownBy(() -> categoryService.getById(2L))
		.isInstanceOf(ApiException.class)
		.hasMessageStartingWith("Category not found.");	
	}
	
	@Test
	public void testUpdateCategory() {
		//Given
		category = new Category();
		category.setCategoryId(1L);
		category.setCategoryName("Java Update");
		category.setIsDeleted((short) 1);
		// When
		Category categoryAfterUpdate = categoryService.update(1L, category);
		// Then
		verify(categoryRepository, atMostOnce()).findById(1L);
		verify(categoryRepository).save(categoryCapture.capture());
		assertEquals(categoryCapture.getValue().getCategoryName(), category.getCategoryName());
	}
	
	@Test
	public void testDeleteCategory() {
		// Given 
		Long categoryID = 1L;
		// When
		categoryService.delete(categoryID);
		// Then
		// verify(categoryRepository).save(categoryCapture.capture());
		verify(categoryRepository, times(1)).deleteById(1l);
	}
	
	@Test
	public void TestGetAll() {
		// Give
		testCreateCategory();
		Map<String, String> params = new HashMap<>();
		params.put("category_id", "1");
		params.put("cate_name", "Java Update");
		params.put("_limit", "2");
		params.put("_page", "2");
		CategoryFilter categoryFilter = new CategoryFilter();
		categoryFilter.setCategoryId( 1l );
		categoryFilter.setCategoryName("Java Update");
		CategorySpec categorySpec = new CategorySpec(categoryFilter);
		Pageable pageable = PageUtil.getPageable(2, 2);
		// When
		categoryService.getAll(params);
		// Then
		assertEquals(1, categoryFilter.getCategoryId());
		assertEquals("Java Update", categoryFilter.getCategoryName());
		verify(categoryRepository, times(1)).findAll(categorySpec, pageable);
		// Thank you bong Sona :)
	}

}
