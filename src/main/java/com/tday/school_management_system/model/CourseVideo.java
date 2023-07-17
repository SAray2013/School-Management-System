package com.tday.school_management_system.model;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(
		name = "tbl_course_videos",
		indexes = {
				@Index(name = "tbl_course_videos_course_video_id_index", columnList = "course_video_id")
		}
)
@Data
public class CourseVideo {

	@Id
	@GeneratedValue(generator = "course_videos_generator")
	@SequenceGenerator(name = "course_videos_generator", initialValue = 1, sequenceName = "course_videos")
	@Column(name = "course_video_id")
	private Long courseVideoId;

	@ManyToOne
	@JoinColumn(name = "course_id")
	@NotNull(message = "{required.field}")
	private Course course;

	@Column(name = "cour_video_title")
	private String courseVideoTitle;

	@Column(name = "cour_video_description")
	private String courseVideoDescription;

	@Column(name = "cour_video_link")
	private String courseVideoLink;

	@Column(name = "cour_video_thumbnail")
	private String courseVideoThumbnail;

	@Column(name = "cour_ordering")
	private Short courseOrdering;

}
