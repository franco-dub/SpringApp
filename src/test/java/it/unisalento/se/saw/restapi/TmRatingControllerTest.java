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
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.unisalento.se.saw.IService.TmRatingIService;
import it.unisalento.se.saw.dto.CourseDto;
import it.unisalento.se.saw.dto.PersonDto;
import it.unisalento.se.saw.dto.StudentDto;
import it.unisalento.se.saw.dto.TeachingMaterialDto;
import it.unisalento.se.saw.dto.TmRatingDto;

@RunWith(MockitoJUnitRunner.class)
public class TmRatingControllerTest {

	ObjectMapper mapper = new ObjectMapper();
	public static final MediaType APPLICATION_JSON_UTF8 = 
			new MediaType(
					MediaType.APPLICATION_JSON.getType(),
					MediaType.APPLICATION_JSON.getSubtype(),
					Charset.forName("utf8"));
	private MockMvc mockMvc;

	@Mock
	private TmRatingIService tmRatingServiceMock;
	TmRatingDto tmRating = new TmRatingDto();
	StudentDto student = new StudentDto();
	TeachingMaterialDto teachingMaterial = new TeachingMaterialDto();
	PersonDto person = new PersonDto();
	CourseDto course = new CourseDto();
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new TmRatingController(tmRatingServiceMock)).build();
		
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
		teachingMaterial.setCreated(new Date());
		teachingMaterial.setFileName("file.pdf");
		teachingMaterial.setFileType("application/pdf");
		teachingMaterial.setSize(23424);
		teachingMaterial.setTeachingMaterialId(1);
		tmRating.setRate("3");
		tmRating.setStudent(student);
		tmRating.setTmRatingId(1);
		tmRating.setTeachingMaterial(teachingMaterial);
	}
	
	@Test
	public void getTmRatingTest() throws Exception{
		
		when(tmRatingServiceMock.findById(1)).thenReturn(tmRating);
		
		mockMvc.perform(get("/tmRating/{id}", 1))
		.andExpect(status().isOk())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8));
		
		verify(tmRatingServiceMock, times(1)).findById(1);
		verifyNoMoreInteractions(tmRatingServiceMock);
	}
	
	@Test
	public void getTmRatingExceptionTest() throws Exception{
		
		when(tmRatingServiceMock.findById(1)).thenThrow(new NullPointerException("Not Found"));
		
		mockMvc.perform(get("/tmRating/{id}", 1))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void createTmRatingTest() throws Exception{

		Mockito.doNothing().when(tmRatingServiceMock).saveTmRating(tmRating);

		mockMvc.perform(post("/tmRating/add")
			.contentType(MediaType.APPLICATION_JSON)
			.content(mapper.writeValueAsString(tmRating)))
			.andExpect(status().isCreated());
		verify(tmRatingServiceMock, times(1)).saveTmRating(Mockito.isA(TmRatingDto.class));
		verifyNoMoreInteractions(tmRatingServiceMock);
	}
	
	@Test
	public void createTmRatingExceptionTest() throws Exception{
		
		tmRating.setStudent(null);

		mockMvc.perform(post("/tmRating/add")
			.contentType(MediaType.APPLICATION_JSON)
			.content(mapper.writeValueAsString(tmRating)))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void findAllTmRatingTest() throws Exception{
		
		List<TmRatingDto> tmRatings = new ArrayList<>();
		tmRatings.add(tmRating);
		tmRatings.add(tmRating);
		tmRatings.add(tmRating);
		
		when(tmRatingServiceMock.findAllTmRatings()).thenReturn(tmRatings);
		
		mockMvc.perform(get("/tmRating/findAll"))
		.andExpect(status().isOk());
		
		verify(tmRatingServiceMock, times(1)).findAllTmRatings();
		verifyNoMoreInteractions(tmRatingServiceMock);
	}
	
	@Test
	public void findAllTmRatingExceptionTest() throws Exception{
		
		List<TmRatingDto> tmRatings = new ArrayList<>();
		
		when(tmRatingServiceMock.findAllTmRatings()).thenReturn(tmRatings);
		
		mockMvc.perform(get("/tmRating/findAll"))
		.andExpect(status().isNoContent());
		
		verify(tmRatingServiceMock, times(1)).findAllTmRatings();
		verifyNoMoreInteractions(tmRatingServiceMock);
	}
	
	@Test
	public void getTmRatingByTeachingMaterialTest() throws Exception {
		
		List<TmRatingDto> tmRatings = new ArrayList<>();
		tmRatings.add(tmRating);
		tmRatings.add(tmRating);
		tmRatings.add(tmRating);
		
		when(tmRatingServiceMock.findByTeachingMaterialTeachingMaterialId(1)).thenReturn(tmRatings);
		mockMvc.perform(get("/tmRating/findByTmId/{id}", 1))
		.andExpect(status().isOk());
		
		verify(tmRatingServiceMock, times(1)).findByTeachingMaterialTeachingMaterialId(1);
		verifyNoMoreInteractions(tmRatingServiceMock);
	}
	
	@Test
	public void getTmRatingByTeachingMaterialException1Test() throws Exception {
		
		List<TmRatingDto> tmRatings = new ArrayList<>();
		
		when(tmRatingServiceMock.findByTeachingMaterialTeachingMaterialId(1)).thenReturn(tmRatings);
		mockMvc.perform(get("/tmRating/findByTmId/{id}", 1))
		.andExpect(status().isNoContent());
		
		verify(tmRatingServiceMock, times(1)).findByTeachingMaterialTeachingMaterialId(1);
		verifyNoMoreInteractions(tmRatingServiceMock);
	}
	
	@Test
	public void getTmRatingByTeachingMaterialException2Test() throws Exception {
		
		when(tmRatingServiceMock.findByTeachingMaterialTeachingMaterialId(1)).thenThrow(new NullPointerException("Not Found"));
		
		mockMvc.perform(get("/tmRating/findByTmId/{id}", 1))
		.andExpect(status().isNotFound());
		
		verify(tmRatingServiceMock, times(1)).findByTeachingMaterialTeachingMaterialId(1);
		verifyNoMoreInteractions(tmRatingServiceMock);
	}
	
	@Test
	public void getTmRatingByStudentIdAndTeachingMateriaIdTest() throws Exception {
		
		when(tmRatingServiceMock.findByStudentIdAndTeachingMaterialId(1, 1)).thenReturn(tmRating);
		
		mockMvc.perform(get("/tmRating/findByStudentAndTM/{studentId}/{teachingMaterialId}", 1, 1))
		.andExpect(status().isOk());
		
		verify(tmRatingServiceMock, times(1)).findByStudentIdAndTeachingMaterialId(1, 1);
		verifyNoMoreInteractions(tmRatingServiceMock);
	}
	
	@Test
	public void getTmRatingByStudentIdAndTeachingMateriaIdExceptionTest() throws Exception {
		
		when(tmRatingServiceMock.findByStudentIdAndTeachingMaterialId(1, 1)).thenThrow(new NullPointerException("Not Found"));
		
		mockMvc.perform(get("/tmRating/findByStudentAndTM/{studentId}/{teachingMaterialId}", 1, 1))
		.andExpect(status().isNoContent());
		
		verify(tmRatingServiceMock, times(1)).findByStudentIdAndTeachingMaterialId(1, 1);
		verifyNoMoreInteractions(tmRatingServiceMock);
	}
}
