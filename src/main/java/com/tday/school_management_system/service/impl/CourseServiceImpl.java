package com.tday.school_management_system.service.impl;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;

import com.tday.school_management_system.model.User;
import com.tday.school_management_system.model.Course;
import com.tday.school_management_system.model.Category;
import com.tday.school_management_system.utils.PageUtil;
import com.tday.school_management_system.spec.CourseSpec;
import com.tday.school_management_system.model.CourseLike;
import com.tday.school_management_system.model.CourseVisit;
import com.tday.school_management_system.spec.CourseFilter;
import com.tday.school_management_system.model.CourseEnroll;
import com.tday.school_management_system.service.UsersService;
import com.tday.school_management_system.dto.CourseDisplayDTO;
import com.tday.school_management_system.spec.CourseEnrollSpec;
import com.tday.school_management_system.service.CourseService;
import com.tday.school_management_system.spec.CourseEnrollFilter;
import com.tday.school_management_system.dto.CourseEnrollDisplayDTO;
import com.tday.school_management_system.repository.CourseRepository;
import com.tday.school_management_system.repository.UsersRepository;
import com.tday.school_management_system.repository.CategoryRepository;
import com.tday.school_management_system.repository.CourseLikeRepository;
import com.tday.school_management_system.repository.CourseVisitRepository;
import com.tday.school_management_system.repository.CourseEnrollRepository;
import com.tday.school_management_system.exception.ResourceNotFoundException;


@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
	UsersService userService;
    @Autowired
    UsersRepository userRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
	CourseLikeRepository courseLikeRepository;
    @Autowired
	CourseVisitRepository courseVisitRepository;
    @Autowired
    CourseEnrollRepository courseEnrollRepository;

    @Override
    public Course create(Course course) {
        Category _cate = course.getCategory();
        categoryRepository.findById(_cate.getCategoryId());
        return courseRepository.save(course);
    }

    @Override
    public Course getById(Long id) {
        return courseRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Course"));
    }

    @Override
    public Course update(Long id, Course course) {
        try{
            getById(id);
            course.setCourseId(id);
            return courseRepository.save(course);
        }catch (Exception e){
            throw new RuntimeException("Invalid Input");
        }
    }

    @Override
    public void delete(Long id) {
        getById(id);
        courseRepository.deleteById(id);
    }

    @Override
    public Page<Course> getAll(Map<String, String> params) {
        CourseFilter courseFilter = new CourseFilter();
        Category category = new Category();

        if (params.containsKey("category_id")) {
            category.setCategoryId(Long.valueOf(params.get("category_id")));
            courseFilter.setCategory(category);
        }

        if (params.containsKey("cour_name")) {
            courseFilter.setCourseName(params.get("cour_name"));
        }


        int pageNumber = PageUtil.DEFAULT_PAGE_NUMBER;
        if (params.containsKey(PageUtil.PAGE_NUMBER)) {
            pageNumber = Integer.parseInt(params.get(PageUtil.PAGE_NUMBER));
        }

        int pageSize = PageUtil.DEFAULT_PAGE_LIMIT;
        if (params.containsKey(PageUtil.PAGE_LIMIT)) {
            pageSize = Integer.parseInt(params.get(PageUtil.PAGE_LIMIT));
        }

        CourseSpec courseSpec = new CourseSpec(courseFilter);

        Pageable pageable = PageUtil.getPageable(pageNumber, pageSize);

        Page<Course> coursePage = courseRepository.findAll(courseSpec, pageable);

        return coursePage;
    }

    @Override
    public List<CourseDisplayDTO> toCourseDisplayDTOs(List<Course> course) {
        List<CourseDisplayDTO> displayDTOs = new ArrayList<>();
        List<Long> categoryIds = course.stream().map(c -> c.getCategory().getCategoryId()).toList();
        List<Category> categories = categoryRepository.findAllById(categoryIds);
        Map<Long,String> categoryMap = categories.stream().collect(Collectors.toMap(c -> c.getCategoryId(), c -> c.getCategoryName()));
        for(Course c: course){
            CourseDisplayDTO dto = new CourseDisplayDTO();
            dto.setCourseId(c.getCourseId());
            dto.setCategoryId(c.getCategory().getCategoryId());
            dto.setCategoryName(categoryMap.get(c.getCategory().getCategoryId()));
            dto.setCourseName(c.getCourseName());
            dto.setCourseDescription(c.getCourseDescription());
            dto.setCoursePrice(c.getCoursePrice());
            displayDTOs.add(dto);
        }
        return displayDTOs;
    }

	@Override
	public void createCourseVisit(CourseVisit courseVisit) {
		getById(courseVisit.getCourse().getCourseId());
		userService.getById(courseVisit.getUser().getId());
		CourseVisit tmp = courseVisitRepository.existsByCourseIdAndUserId(courseVisit.getCourse().getCourseId(), courseVisit.getUser().getId());
		if(tmp != null) {
			throw new RuntimeException("This user already visited.");
		}else {
			courseVisitRepository.save(courseVisit);
		}
	}

	@Override
	public void createCourseLike(CourseLike courseLike) {
		getById(courseLike.getCourse().getCourseId());
		userService.getById(courseLike.getUser().getId());
		CourseLike tmp = courseLikeRepository.existsByCourseIdAndUserId(courseLike.getCourse().getCourseId(), courseLike.getUser().getId());
		if(tmp != null) {
			throw new RuntimeException("This user already liked.");
		}else {
			courseLikeRepository.save(courseLike);	
		}
	}

	@Override
	public void enrollCourse(CourseEnroll courseEnroll) {
		getById(courseEnroll.getCourse().getCourseId());
		userService.getById(courseEnroll.getUser().getId());
		CourseEnroll tmp = courseEnrollRepository.existsByCourseIdAndUserId(courseEnroll.getCourse().getCourseId(), courseEnroll.getUser().getId());
		if(tmp != null) {
			throw new RuntimeException("This user already enrolled.");
		}else {
			courseEnroll.setIsApproved((short) 0);
			courseEnrollRepository.save(courseEnroll);	
		}
	}
	
	private CourseEnroll getCourseEnroll(Long id) {
		return courseEnrollRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Course Enroll ID"));
	}

	@Override
	public void approveCourse(Long id) {
		CourseEnroll courseEnroll = getCourseEnroll(id);
		courseEnroll.setIsApproved((short) 1);
		courseEnrollRepository.save(courseEnroll);	
	}

	@Override
	public void rejectCourse(Long id) {
		CourseEnroll courseEnroll = getCourseEnroll(id);
		courseEnroll.setIsApproved((short) 2);
		courseEnrollRepository.save(courseEnroll);	
	}

	@Override
	public Page<CourseEnroll> getListCourseEnroll(Map<String, String> params) {
		CourseEnrollFilter courseEnrollFilter = new CourseEnrollFilter();
        User user = new User();
        Course course = new Course();

        if (params.containsKey("user_id")) {
            user.setId(Long.valueOf(params.get("user_id")));
            courseEnrollFilter.setUser(user);
        }
        
        if (params.containsKey("user_first_name")) {
            user.setFirstName(params.get("user_first_name"));
            courseEnrollFilter.setUser(user);
        }
        
        if (params.containsKey("user_last_name")) {
            user.setLastName(params.get("user_last_name"));
            courseEnrollFilter.setUser(user);
        }

        if (params.containsKey("course_id")) {
        	course.setCourseId(Long.valueOf(params.get("course_id")));
            courseEnrollFilter.setCourse(course);
        }
        
        if (params.containsKey("course_name")) {
        	course.setCourseName(params.get("course_name"));
            courseEnrollFilter.setCourse(course);
        }


        int pageNumber = PageUtil.DEFAULT_PAGE_NUMBER;
        if (params.containsKey(PageUtil.PAGE_NUMBER)) {
            pageNumber = Integer.parseInt(params.get(PageUtil.PAGE_NUMBER));
        }

        int pageSize = PageUtil.DEFAULT_PAGE_LIMIT;
        if (params.containsKey(PageUtil.PAGE_LIMIT)) {
            pageSize = Integer.parseInt(params.get(PageUtil.PAGE_LIMIT));
        }

        CourseEnrollSpec courseEnrollSpec = new CourseEnrollSpec(courseEnrollFilter);

        Pageable pageable = PageUtil.getPageable(pageNumber, pageSize);

        Page<CourseEnroll> courseEnrollPage = courseEnrollRepository.findAll(courseEnrollSpec, pageable);

        return courseEnrollPage;
	}

	@Override
	public List<CourseEnrollDisplayDTO> toCourseEnrollDisplayDTOs(List<CourseEnroll> courseEnroll) {
		List<CourseEnrollDisplayDTO> displayDTOs = new ArrayList<>();
		List<Long> courseIds = courseEnroll.stream().map(c -> c.getCourse().getCourseId()).toList();
		List<Course> courses = courseRepository.findAllById(courseIds);
		Map<Long,Course> courseMap = courses.stream().collect(Collectors.toMap(c -> c.getCourseId(), c -> c));
		List<Long> userIds = courseEnroll.stream().map(c -> c.getUser().getId()).toList();
		List<User> users = userRepository.findAllById(userIds);
		Map<Long,User> userMap = users.stream().collect(Collectors.toMap(c -> c.getId(), c -> c));
		for(CourseEnroll enroll : courseEnroll) {
			CourseEnrollDisplayDTO tmp = new CourseEnrollDisplayDTO();
			tmp.setCourseEnrollId(enroll.getCourseEnrollId());
			tmp.setCourseId(enroll.getCourse().getCourseId());
			tmp.setCourseName(courseMap.get(enroll.getCourse().getCourseId()).getCourseName());
			tmp.setCoursePrice(courseMap.get(enroll.getCourse().getCourseId()).getCoursePrice());
			tmp.setUserId(enroll.getUser().getId());
			tmp.setUserFirstName(userMap.get(enroll.getUser().getId()).getFirstName());
			tmp.setUserLastName(userMap.get(enroll.getUser().getId()).getLastName());
			tmp.setIsApproved(enroll.getIsApproved());
			displayDTOs.add(tmp);
		}
		return displayDTOs;
	}
}
