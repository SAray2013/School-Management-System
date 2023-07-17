package com.tday.school_management_system.spec;

import lombok.Data;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.domain.Specification;
import com.tday.school_management_system.model.Category;

@Data
public class CategorySpec implements Specification<Category> {

    private final CategoryFilter categoryFilter;

    List<Predicate> predicates = new ArrayList<>();

    @Override
    public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        if (categoryFilter.getCategoryId() != null) {
            Predicate id = root.get("categoryId").in(categoryFilter.getCategoryId());
            predicates.add(id);
        }
        if (categoryFilter.getCategoryName() != null) {
            Predicate name = criteriaBuilder.like(criteriaBuilder.upper(root.get("categoryName")),
                    "%"+ categoryFilter.getCategoryName().toUpperCase()+ "%");
            predicates.add(name);
        }

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

}
