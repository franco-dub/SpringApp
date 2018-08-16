package it.unisalento.se.saw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.IService.CourseIService;
import it.unisalento.se.saw.domain.Course;
import it.unisalento.se.saw.repo.CourseRepository;

@Service
public class CourseService implements CourseIService {
	
	CourseRepository courseRepository;

	@Autowired
	public CourseService(CourseRepository courseRepository) {
		super();
		this.courseRepository = courseRepository;
	}

	@Override
	@Transactional
	public Course findById(Integer id) {
		return courseRepository.findById(id).get();
	}

	@Override
	public void saveCourse(Course course) {
		courseRepository.save(course);
	}

	@Override
	@Transactional
	public void updateCourse(Course course) {
		saveCourse(course);
	}

	@Override
	@Transactional
	public void deleteCourseById(Integer id) {
		courseRepository.deleteById(id);
	}

	@Override
	@Transactional
	public List<Course> findAllCourses() {
		return courseRepository.findAll();
	}

	@Override
	@Transactional
	public boolean isCourseExist(Course course) {
		return findById(course.getCourseId()) != null;
	}

}
