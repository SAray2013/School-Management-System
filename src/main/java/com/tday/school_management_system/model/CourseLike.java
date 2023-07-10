package com.tday.school_management_system.model;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(
		name = "tbl_course_like"
)
@Data
public class CourseLike {
	
	@Id
	@GeneratedValue(generator = "course_like_generator")
	@SequenceGenerator(name = "course_likes_generator", initialValue = 1, sequenceName = "course_like")
	@Column(name = "course_like_id")
	private Long courseLikeId;

	@ManyToOne
	@JoinColumn(name = "course_id")
	@NotNull(message = "{required.field}")
	private Course course;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@NotNull(message = "{required.field}")
	private User user;

}
