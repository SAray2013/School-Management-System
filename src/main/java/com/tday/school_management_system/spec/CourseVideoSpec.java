package com.tday.school_management_system.spec;

import lombok.Data;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaBuilder;

import org.springframework.data.jpa.domain.Specification;

import com.tday.school_management_system.model.CourseVideo;

@Data
public class CourseVideoSpec implements Specification<CourseVideo>{
	
	private final CourseVideoFilter courseVideoFilter;
	List<Predicate> predicates = new ArrayList<>();
	
	@Override
	public Predicate toPredicate(Root<CourseVideo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		
		if(courseVideoFilter.getCourse() != null) {
			Predicate courId = root.get("course").get("courseId").in(courseVideoFilter.getCourse().getCourseId());
            predicates.add(courId);
		}
		
		if(courseVideoFilter.getCourseVideoTitle() != null) {
			Predicate courVideoTitle = criteriaBuilder.like(criteriaBuilder.upper(root.get("courseVideoTitle")),
                    "%"+ courseVideoFilter.getCourseVideoTitle().toUpperCase()+ "%");
            predicates.add(courVideoTitle);
		}
		
		return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
	}

}
