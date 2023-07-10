package com.tday.school_management_system.spec;

import lombok.Data;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import com.tday.school_management_system.model.Course;

@Data
public class CourseSpec implements Specification<Course> {

    private final CourseFilter courseFilter;

    List<Predicate> predicates = new ArrayList<>();

    @Override
    public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    	
        if(courseFilter.getCategory() != null) {
            Predicate cateId = root.get("category").get("categoryId").in(courseFilter.getCategory().getCategoryId());
            predicates.add(cateId);
        }

        if (courseFilter.getCourseName() != null) {
            Predicate courseName = criteriaBuilder.like(criteriaBuilder.upper(root.get("courseName")),
                    "%"+ courseFilter.getCourseName().toUpperCase()+ "%");
            predicates.add(courseName);
        }

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

}
