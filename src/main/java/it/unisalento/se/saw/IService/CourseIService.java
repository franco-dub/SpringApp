package it.unisalento.se.saw.IService;

import java.util.List;

import it.unisalento.se.saw.dto.CourseDto;

public interface CourseIService {
	public CourseDto findById(Integer id);
	public void saveCourse(CourseDto courseDto);
	public void updateCourse(CourseDto courseDto);
	public void deleteCourseById(Integer id);
	public List<CourseDto> findAllCourses();
}
