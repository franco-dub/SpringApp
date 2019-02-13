package it.unisalento.se.saw.restapi;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import it.unisalento.se.saw.IService.StudentIService;
import it.unisalento.se.saw.dto.CourseDto;
import it.unisalento.se.saw.dto.PersonDto;
import it.unisalento.se.saw.dto.StudentDto;



@RunWith(MockitoJUnitRunner.class)
public class StudentControllerTest {
	
	ObjectMapper mapper = new ObjectMapper();
	public static final MediaType APPLICATION_JSON_UTF8 = 
			new MediaType(
					MediaType.APPLICATION_JSON.getType(),
					MediaType.APPLICATION_JSON.getSubtype(),
					Charset.forName("utf8"));
	private MockMvc mockMvc;

	@Mock
	private StudentIService studentServiceMock;
	StudentDto student = new StudentDto();
	PersonDto person = new PersonDto();
	CourseDto course = new CourseDto();
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new StudentController(studentServiceMock)).build();

		course.setCfu(180);
		course.setCourseId(1);
		course.setCourseType("BACHELOR");
		course.setDescription("no description for me");
		course.setName("Ingegneria");
		course.setYear(3);
		person.setAddress("via Milano");
		person.setDateOfBirth(new Date(89, 5, 20));
		person.setEmail("email@email.it");
		person.setFirstName("Franco");
		person.setGender("M");
		person.setLastName("Savino");
		person.setPassword("password");
		person.setPersonId(1);
		person.setPhone("5555555555");
		student.setCourse(course);
		student.setPerson(person);
		student.setStudentId(1);
		student.setRegistrationDate(new Date(118, 9, 11));
	}
	
	@Test
	public void getStudentTest() throws Exception{

		
		when(studentServiceMock.findById(1)).thenReturn(student);
		
		mockMvc.perform(get("/student/{id}", 1))
		.andExpect(status().isOk())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$.person.firstName", is("Franco")))
		.andExpect(jsonPath("$.person.lastName", is("Savino")));
		
		verify(studentServiceMock, times(1)).findById(1);
		verifyNoMoreInteractions(studentServiceMock);
	}
	
	@Test
	public void createStudentTest() throws Exception{
		
		Mockito.doNothing().when(studentServiceMock).saveStudent(student);
		
	    mockMvc.perform(post("/student/add")
	    		.contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(student)))
        	.andExpect(status().isCreated());
        verify(studentServiceMock, times(1)).saveStudent(Mockito.isA(StudentDto.class));
	    verifyNoMoreInteractions(studentServiceMock);
	}

	@Test
	public void createStudentExceptionTest() throws Exception{

		student.setPerson(null);
		
	    mockMvc.perform(post("/student/add")
	    		.contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(student)))
	    		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getStudentExceptionTest() throws Exception{
		
		when(studentServiceMock.findById(-1)).thenThrow(new NullPointerException("Not Found"));
		
		mockMvc.perform(get("/student/{id}", -1))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void findAllStudentTest() throws Exception {
		List<StudentDto> students = new ArrayList<>();
		students.add(student);
		students.add(student);
		students.add(student);
		
		when(studentServiceMock.findAllStudents()).thenReturn(students);
		
		mockMvc.perform(get("/student/findAll"))
		.andExpect(status().isOk());
		
		verify(studentServiceMock, times(1)).findAllStudents();
		verifyNoMoreInteractions(studentServiceMock);
	}
	
	@Test
	public void findAllStudentExceptionTest() throws Exception {
		List<StudentDto> students = new ArrayList<>();
		
		when(studentServiceMock.findAllStudents()).thenReturn(students);
		
		mockMvc.perform(get("/student/findAll"))
		.andExpect(status().isNoContent());
		
		verify(studentServiceMock, times(1)).findAllStudents();
		verifyNoMoreInteractions(studentServiceMock);
	}
	
	@Test
	public void updateStudentTest() throws Exception {
		when(studentServiceMock.findById(1)).thenReturn(student);
		Mockito.doNothing().when(studentServiceMock).updateStudent(student);
		
	    mockMvc.perform(put("/student/{id}", 1)
	    		.contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(student)))
        	.andExpect(status().isOk());
	    
	    verify(studentServiceMock, times(1)).findById(1);
        verify(studentServiceMock, times(1)).updateStudent(Mockito.isA(StudentDto.class));
	    verifyNoMoreInteractions(studentServiceMock);
	}
	
	@Test
	public void updateStudentException1Test() throws Exception {
		student.setPerson(null);
		when(studentServiceMock.findById(1)).thenReturn(student);
		Mockito.doNothing().when(studentServiceMock).updateStudent(student);
		
	    mockMvc.perform(put("/student/{id}", 1)
	    	.contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(student)))
        	.andExpect(status().isBadRequest());
	}
	
	@Test
	public void updateStudentException2Test() throws Exception {
		
		when(studentServiceMock.findById(1)).thenThrow(new NullPointerException("Not found"));
		Mockito.doNothing().when(studentServiceMock).updateStudent(student);
		
	    mockMvc.perform(put("/student/{id}", 1)
	    	.contentType(MediaType.APPLICATION_JSON)
	    	.content(mapper.writeValueAsString(student)))
        	.andExpect(status().isNotFound());
	}

	

	@Test
	public void deleteStudentTest() throws Exception {
		
		when(studentServiceMock.findById(1)).thenReturn(student);
		mockMvc.perform(delete("/student/{id}", 1))
        	.andExpect(status().isNoContent());
		
	}
	
	@Test
	public void deleteStudentExceptionTest() throws Exception {
		
		when(studentServiceMock.findById(1)).thenThrow(new NullPointerException("Not Found"));
		mockMvc.perform(delete("/student/{id}", 1))
        	.andExpect(status().isNotFound());
	}
	
	@Test
	public void listAllCourseSStudentsTest() throws Exception {
		
		List<StudentDto> students = new ArrayList<>();
		students.add(student);
		students.add(student);
		students.add(student);
		
		when(studentServiceMock.findAllCourseSStudent(1)).thenReturn(students);
		
		mockMvc.perform(get("/student/findAll/{id}", 1))
		.andExpect(status().isOk());
		
		verify(studentServiceMock, times(1)).findAllCourseSStudent(1);
		verifyNoMoreInteractions(studentServiceMock);
	}
	
	@Test
	public void listAllCourseSStudentsExceptionTest() throws Exception {
		
		List<StudentDto> students = new ArrayList<>();
		
		when(studentServiceMock.findAllCourseSStudent(1)).thenReturn(students);
		
		mockMvc.perform(get("/student/findAll/{id}", 1))
		.andExpect(status().isNoContent());
		
		verify(studentServiceMock, times(1)).findAllCourseSStudent(1);
		verifyNoMoreInteractions(studentServiceMock);
	}
}
