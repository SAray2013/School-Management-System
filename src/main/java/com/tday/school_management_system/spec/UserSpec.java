package com.tday.school_management_system.spec;

import lombok.Data;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaBuilder;

import org.springframework.data.jpa.domain.Specification;

import com.tday.school_management_system.model.User;


@Data
public class UserSpec implements Specification<User>{
	
	private final UserFilter userFilter;
	List<Predicate> predicates = new ArrayList<>();
	
	@Override
	public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		
		if (userFilter.getId() != null) {
            Predicate id = root.get("id").in(userFilter.getId());
            predicates.add(id);
        }
        if (userFilter.getUserName() != null) {
            Predicate name = criteriaBuilder.like(criteriaBuilder.upper(root.get("username")),
                    "%"+ userFilter.getUserName().toUpperCase()+ "%");
            predicates.add(name);
        }
		
		return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
	}

}