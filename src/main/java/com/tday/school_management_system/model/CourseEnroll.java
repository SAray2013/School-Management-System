package com.tday.school_management_system.model;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Comment;

@Entity
@Table(
		name = "tbl_course_enroll"
)
@Data
public class CourseEnroll {
	
	@Id
	@GeneratedValue(generator = "course_enroll_generator")
	@SequenceGenerator(name = "course_enrolls_generator", initialValue = 1, sequenceName = "course_enroll")
	@Column(name = "course_enroll_id")
	private Long courseEnrollId;

	@ManyToOne
	@JoinColumn(name = "course_id")
	@NotNull(message = "{required.field}")
	private Course course;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@NotNull(message = "{required.field}")
	private User user;
	
	@Column(name = "is_approved")
    @Comment("0: Pending , 1: Approved , 2: Reject")
    private Short isApproved;

}
