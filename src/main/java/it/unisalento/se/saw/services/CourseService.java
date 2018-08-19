package it.unisalento.se.saw.services;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.IService.CourseIService;
import it.unisalento.se.saw.domain.Course;
import it.unisalento.se.saw.dto.CourseDto;
import it.unisalento.se.saw.repo.CourseRepository;

@Service
public class CourseService implements CourseIService {
	
	private static final ModelMapper modelMapper = new ModelMapper();
	CourseRepository courseRepository;

	@Autowired
	public CourseService(CourseRepository courseRepository) {
		super();
		this.courseRepository = courseRepository;
	}

	@Override
	@Transactional
	public CourseDto findById(Integer id) {
		Course course = courseRepository.findById(id).get();
		CourseDto courseDto = modelMapper.map(course, CourseDto.class);
		return courseDto;
	}

	@Override
	@Transactional
	public void saveCourse(CourseDto courseDto) {
		Course course = modelMapper.map(courseDto, Course.class);
		courseRepository.save(course);
	}

	@Override
	@Transactional
	public void updateCourse(CourseDto courseDto) {
		saveCourse(courseDto);
	}

	@Override
	@Transactional
	public void deleteCourseById(Integer id) {
		courseRepository.deleteById(id);
	}

	@Override
	@Transactional
	public List<CourseDto> findAllCourses() {
		List<Course> courses = courseRepository.findAll();
		Type targetListType = new TypeToken<List<CourseDto>>() {}.getType();
		List<CourseDto> courseDtos = modelMapper.map(courses, targetListType);
		return courseDtos;
	}
}
