package com.tday.school_management_system.service.impl;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;

import com.tday.school_management_system.model.Course;
import com.tday.school_management_system.utils.PageUtil;
import com.tday.school_management_system.model.CourseVideo;
import com.tday.school_management_system.spec.CourseVideoSpec;
import com.tday.school_management_system.spec.CourseVideoFilter;
import com.tday.school_management_system.dto.CourseVideoDisplayDTO;
import com.tday.school_management_system.service.CourseVideoService;
import com.tday.school_management_system.repository.CourseRepository;
import com.tday.school_management_system.repository.CourseVideoRepository;
import com.tday.school_management_system.exception.ResourceNotFoundException;

@Service
public class CourseVideoServiceImpl implements CourseVideoService {
	
	@Autowired
	CourseVideoRepository courseVideoRepository;
	@Autowired
    CourseRepository courseRepository;

	@Override
	public CourseVideo create(CourseVideo courseVideo) {
		return courseVideoRepository.save(courseVideo);
	}

	@Override
	public CourseVideo getById(Long id) {
		return courseVideoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Video"));
	}

	@Override
	public CourseVideo update(Long id, CourseVideo courseVideo) {
		try {
			getById(id);
			courseVideo.setCourseVideoId(id);
			return courseVideoRepository.save(courseVideo);
		} catch (Exception e) {
			throw new RuntimeException("Invalid Input");
		}
	}

	@Override
	public void delete(Long id) {
		getById(id);
		courseVideoRepository.deleteById(id);
	}

	@Override
	public Page<CourseVideo> getAll(Map<String, String> params) {
		CourseVideoFilter courseVideoFilter =  new CourseVideoFilter();
		Course course = new Course();
		
		if (params.containsKey("course_id")) {
			course.setCourseId(Long.valueOf(params.get("course_id")));
			courseVideoFilter.setCourse(course);
        }
		
		if (params.containsKey("course_video_title")) {
			courseVideoFilter.setCourseVideoTitle(params.get("course_video_title"));
        }
		
		int pageNumber = PageUtil.DEFAULT_PAGE_NUMBER;
        if (params.containsKey(PageUtil.PAGE_NUMBER)) {
            pageNumber = Integer.parseInt(params.get(PageUtil.PAGE_NUMBER));
        }

        int pageSize = PageUtil.DEFAULT_PAGE_LIMIT;
        if (params.containsKey(PageUtil.PAGE_LIMIT)) {
            pageSize = Integer.parseInt(params.get(PageUtil.PAGE_LIMIT));
        }
        
        CourseVideoSpec courseVideoSpec = new CourseVideoSpec(courseVideoFilter);
        
        Pageable pageable = PageUtil.getPageable(pageNumber, pageSize);
        
        Page<CourseVideo> coursePage = courseVideoRepository.findAll(courseVideoSpec, pageable);
		
		return coursePage;
	}

	@Override
	public List<CourseVideoDisplayDTO> toCourseVideoDisplayDTOs(List<CourseVideo> courseVideo) {
		List<CourseVideoDisplayDTO> displayDTOs = new ArrayList<>();
		List<Long> courseIds = courseVideo.stream().map(c -> c.getCourse().getCourseId()).toList();
		List<Course> courses = courseRepository.findAllById(courseIds);
		Map<Long,String> courseMap = courses.stream().collect(Collectors.toMap(c -> c.getCourseId(), c -> c.getCourseName()));
		for(CourseVideo c: courseVideo){
			CourseVideoDisplayDTO dto = new CourseVideoDisplayDTO();
			dto.setCourseVideoId(c.getCourseVideoId());
			dto.setCourseId(c.getCourse().getCourseId());
			dto.setCourseName(courseMap.get(c.getCourse().getCourseId()));
			dto.setCourseVideoTitle(c.getCourseVideoTitle());
			dto.setCourseVideoDescription(c.getCourseVideoDescription());
			dto.setCourseVideoThumbnail(c.getCourseVideoThumbnail());
			dto.setCourseVideoLink(c.getCourseVideoLink());
			dto.setCourseOrdering(c.getCourseOrdering());
			displayDTOs.add(dto);
		}
		return displayDTOs;
	}

	@Override
	public List<CourseVideoDisplayDTO> toPreviewCourseVideoDisplayDTOs(List<CourseVideo> courseVideo) {
		List<CourseVideoDisplayDTO> displayDTOs = new ArrayList<>();
		List<Long> courseIds = courseVideo.stream().map(c -> c.getCourse().getCourseId()).toList();
		List<Course> courses = courseRepository.findAllById(courseIds);
		Map<Long,String> courseMap = courses.stream().collect(Collectors.toMap(c -> c.getCourseId(), c -> c.getCourseName()));
		int tmpCount = 1;
		for(CourseVideo c: courseVideo){
			CourseVideoDisplayDTO dto = new CourseVideoDisplayDTO();
			dto.setCourseVideoId(c.getCourseVideoId());
			dto.setCourseId(c.getCourse().getCourseId());
			dto.setCourseName(courseMap.get(c.getCourse().getCourseId()));
			dto.setCourseVideoTitle(c.getCourseVideoTitle());
			dto.setCourseVideoDescription(c.getCourseVideoDescription());
			dto.setCourseVideoThumbnail(c.getCourseVideoThumbnail());
			if(tmpCount <= 3) dto.setCourseVideoLink(c.getCourseVideoLink());
			dto.setCourseOrdering(c.getCourseOrdering());
			displayDTOs.add(dto);
			tmpCount++;
		}
		return displayDTOs;
	}

}
