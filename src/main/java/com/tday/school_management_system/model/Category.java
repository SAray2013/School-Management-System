package com.tday.school_management_system.model;

import lombok.Data;
import javax.persistence.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(
		name = "tbl_categories",
		indexes = {
				@Index(name = "tbl_categories_category_id_index", columnList = "category_id")
		}
)
@Data
public class Category extends AuditEntity {

	@Id
	@GeneratedValue(generator = "categories_generator")
	@SequenceGenerator(name = "categories_generator", initialValue = 1, sequenceName = "categories")
	@Column(name = "category_id")
	private Long categoryId;
	
	@Column(name = "cate_name", nullable = false, unique = true)
	private String categoryName;

	@Column(name = "cate_ordering")
	private Short categoryOrdering;

	@Column(name = "is_deleted" , columnDefinition = "integer default 0")
	@Comment("0: Keep , 1: Remove")
	private Short isDeleted;
	
}
