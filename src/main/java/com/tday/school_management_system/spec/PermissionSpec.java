package com.tday.school_management_system.spec;

import lombok.Data;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaBuilder;

import org.springframework.data.jpa.domain.Specification;

import com.tday.school_management_system.model.Permission;


@Data
public class PermissionSpec implements Specification<Permission>{
	
	private final PermissionFilter permissionFilter;
	List<Predicate> predicates = new ArrayList<>();
	
	@Override
	public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		
		if (permissionFilter.getId() != null) {
            Predicate id = root.get("id").in(permissionFilter.getId());
            predicates.add(id);
        }
        if (permissionFilter.getName() != null) {
            Predicate name = criteriaBuilder.like(criteriaBuilder.upper(root.get("name")),
                    "%"+ permissionFilter.getName().toUpperCase()+ "%");
            predicates.add(name);
        }
		
		return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
	}

}