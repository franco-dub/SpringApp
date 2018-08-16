package it.unisalento.se.saw.IService;

import java.util.List;

import it.unisalento.se.saw.domain.Course;

public interface CourseIService {
	public Course findById(Integer id);
	public void saveCourse(Course course);
	public void updateCourse(Course course);
	public void deleteCourseById(Integer id);
	public List<Course> findAllCourses();
	public boolean isCourseExist(Course course);
}
