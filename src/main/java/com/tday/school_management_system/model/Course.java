package com.tday.school_management_system.model;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

@Entity
@Table(
		name = "tbl_courses",
		indexes = {
				@Index(name = "tbl_courses_course_id_index", columnList = "course_id")
		}
)
@Data
public class Course {

	@Id
	@GeneratedValue(generator = "courses_generator")
	@SequenceGenerator(name = "courses_generator", initialValue = 1, sequenceName = "courses")
	@Column(name = "course_id")
	private Long courseId;

	@ManyToOne
	@JoinColumn(name = "category_id")
	@NotNull(message = "{required.field}")
	private Category category;

	@Column(name = "cour_name")
	private String courseName;

	@Column(name = "cour_description")
	private String courseDescription;

	@Column(name = "cour_price")
	private BigDecimal coursePrice;

}
