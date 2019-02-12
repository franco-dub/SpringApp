package it.unisalento.se.saw.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unisalento.se.saw.IService.CourseIService;
import it.unisalento.se.saw.dto.CourseDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CourseControllerTest{

	private MockMvc mockMvc;

	@Mock
	private CourseIService courseServiceMock;

	private ObjectMapper mapper = new ObjectMapper();

	private CourseDto courseDto = new CourseDto();

	@Before
	public void setUp(){
		mockMvc = MockMvcBuilders
				.standaloneSetup(new CourseController(courseServiceMock))
				.build();

		courseDto.setName("Il Grande Corso");
		courseDto.setCourseId(1);
	}


	@Test
	public void createCourseTest() throws Exception{
		doNothing().when(courseServiceMock).saveCourse(courseDto);
		mockMvc.perform(post("/course/add")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(courseDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated());
	}

	@Test
	public void createNullCourseTest() throws Exception{
		doNothing().doThrow(new NullPointerException()).when(courseServiceMock).saveCourse(null);
		mockMvc.perform(post("/course/add")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(null))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void listAllCourses() throws Exception{
		List<CourseDto> courseDtoList = new ArrayList<>();
		for(int i = 0; i < 5; i++) courseDtoList.add(courseDto);
		when(courseServiceMock.findAllCourses()).thenReturn(courseDtoList);
		mockMvc.perform(get("/course/findAll"))
				.andExpect(status().isOk());
	}

	@Test
	public void listEmptyAllCourses() throws Exception{
		when(courseServiceMock.findAllCourses()).thenReturn(new ArrayList<>());
		mockMvc.perform(get("/course/findAll"))
				.andExpect(status().isNoContent());
	}

	@Test
	public void getCourse() throws Exception{
		when(courseServiceMock.findById(10)).thenReturn(courseDto);
		mockMvc.perform(get("/course/{id}", 10))
				.andExpect(status().isOk());
	}

	@Test
	public void getEmptyCourse() throws Exception{
		when(courseServiceMock.findById(10)).thenThrow(new NullPointerException());
		mockMvc.perform(get("/course/{id}", 10))
				.andExpect(status().isNotFound());
	}

	@Test
	public void updateCourse() throws Exception{
		when(courseServiceMock.findById(10)).thenReturn(courseDto);
		doNothing().when(courseServiceMock).updateCourse(courseDto);
		mockMvc.perform(put("/course/{id}", 10)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(courseDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
	}

	@Test
	public void updateNullCourse() throws Exception{
		when(courseServiceMock.findById(0)).thenReturn(null);
		mockMvc.perform(put("/course/{id}", 0)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(courseDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound());
	}


	@Test
	public void updateWrongCourse() throws Exception{
		when(courseServiceMock.findById(10)).thenReturn(courseDto);
		courseDto.setName(null);
		doNothing().doThrow(new NullPointerException()).when(courseServiceMock).updateCourse(courseDto);
		mockMvc.perform(put("/course/{id}", 10)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(courseDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void deleteCourse() throws Exception{
		when(courseServiceMock.findById(10)).thenReturn(courseDto);
		doNothing().when(courseServiceMock).deleteCourseById(10);
		mockMvc.perform(delete("/course/{id}", 10))
				.andExpect(status().isNoContent());
	}

	@Test
	public void deleteNullCourse() throws Exception{
		when(courseServiceMock.findById(0)).thenReturn(null);
		doNothing().when(courseServiceMock).deleteCourseById(0);
		mockMvc.perform(delete("/course/{id}", 0))
				.andExpect(status().isNotFound());
	}


}