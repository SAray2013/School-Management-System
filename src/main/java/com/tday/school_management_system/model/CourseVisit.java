package com.tday.school_management_system.model;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(
		name = "tbl_course_visit"
)
@Data
public class CourseVisit {
	
	@Id
	@GeneratedValue(generator = "course_visit_generator")
	@SequenceGenerator(name = "course_visits_generator", initialValue = 1, sequenceName = "course_visit")
	@Column(name = "course_visit_id")
	private Long courseVisitId;

	@ManyToOne
	@JoinColumn(name = "course_id")
	@NotNull(message = "{required.field}")
	private Course course;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@NotNull(message = "{required.field}")
	private User user;

}
