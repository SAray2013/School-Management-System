package com.tday.school_management_system.spec;

import lombok.Data;
import com.tday.school_management_system.model.Category;

@Data
public class CourseFilter {
    private Category category;
    private String courseName;
}
