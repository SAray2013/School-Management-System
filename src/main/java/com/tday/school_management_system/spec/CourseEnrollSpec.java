package com.tday.school_management_system.spec;

import lombok.Data;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaBuilder;

import org.springframework.data.jpa.domain.Specification;

import com.tday.school_management_system.model.CourseEnroll;

@Data
public class CourseEnrollSpec implements Specification<CourseEnroll> {

    private final CourseEnrollFilter courseEnrollFilter;

    List<Predicate> predicates = new ArrayList<>();

    @Override
    public Predicate toPredicate(Root<CourseEnroll> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    	
        if(courseEnrollFilter.getCourse() != null && courseEnrollFilter.getCourse().getCourseId() != null) {
            Predicate courseId = root.get("course").get("courseId").in(courseEnrollFilter.getCourse().getCourseId());
            predicates.add(courseId);
        }
        
        if(courseEnrollFilter.getCourse() != null && courseEnrollFilter.getCourse().getCourseName() != null) {
            Predicate courseName = criteriaBuilder.like(criteriaBuilder.upper(root.get("course").get("courseName")),
                    "%"+ courseEnrollFilter.getCourse().getCourseName().toUpperCase()+ "%");
            predicates.add(courseName);
        }

        if(courseEnrollFilter.getUser() != null && courseEnrollFilter.getUser().getId() != null) {
            Predicate userId = root.get("user").get("id").in(courseEnrollFilter.getUser().getId());
            predicates.add(userId);
        }
        
        if(courseEnrollFilter.getUser() != null && courseEnrollFilter.getUser().getFirstName() != null) {
            Predicate userFirstName = criteriaBuilder.like(criteriaBuilder.upper(root.get("user").get("firstName")),
                    "%"+ courseEnrollFilter.getUser().getFirstName().toUpperCase() + "%");
            predicates.add(userFirstName);
        }
        
        if(courseEnrollFilter.getUser() != null && courseEnrollFilter.getUser().getLastName() != null) {
            Predicate userLastName = criteriaBuilder.like(criteriaBuilder.upper(root.get("user").get("lastName")),
                    "%"+ courseEnrollFilter.getUser().getLastName().toUpperCase() + "%");
            predicates.add(userLastName);
        }
        
        
        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

}
