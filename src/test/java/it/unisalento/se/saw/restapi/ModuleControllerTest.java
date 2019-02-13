package it.unisalento.se.saw.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.unisalento.se.saw.IService.ModuleIService;

import it.unisalento.se.saw.dto.CourseDto;
import it.unisalento.se.saw.dto.ModuleDto;
import it.unisalento.se.saw.dto.PersonDto;
import it.unisalento.se.saw.dto.ProfessorDto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ModuleControllerTest{

	private MockMvc mockMvc;

	@Mock
	ModuleIService moduleServiceMock;

	private ModuleDto moduleDto = new ModuleDto();
	private CourseDto courseDto = new CourseDto();
	private PersonDto personDto = new PersonDto();
	private ProfessorDto professorDto = new ProfessorDto();

	private ObjectMapper mapper = new ObjectMapper();


	@Before
	public void setUp(){
		mockMvc = MockMvcBuilders
				.standaloneSetup(new ModuleController(moduleServiceMock))
				.build();

		moduleDto.setModuleId(1);
		moduleDto.setCredits(6);
		moduleDto.setYear(1);
		moduleDto.setTitle("testModule");
		moduleDto.setSemester("1");

		personDto.setFirstName("Andrea");
		personDto.setLastName("Chezzinoinoino");
		personDto.setPersonId(70);
		personDto.setEmail("ciao");
		personDto.setPassword("franco");
		personDto.setPhone("phone");
		personDto.setAddress("2000-01-01");
		personDto.setGender("m");

		professorDto.setProfessorId(12);
		professorDto.setPerson(personDto);
		professorDto.setHireDate(new Date());

		courseDto.setName("Il Grande Corso");
		courseDto.setCourseId(1);
		moduleDto.setCourse(courseDto);
		moduleDto.setProfessor(professorDto);


	}


	@Test
	public void createModuleTest() throws Exception{
		when(moduleServiceMock.saveModule(moduleDto)).thenReturn(moduleDto);
		mockMvc.perform(post("/module/add")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(moduleDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated());
	}

	@Test
	public void createNullModuleTest() throws Exception{
		moduleDto.setCourse(null);
		when(moduleServiceMock.saveModule(moduleDto)).thenThrow(new NullPointerException());
		mockMvc.perform(post("/module/add")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(moduleDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void listAllModules() throws Exception{
		List<ModuleDto> moduleDtoList = new ArrayList<>();
		for(int i = 0; i < 5; i++) moduleDtoList.add(moduleDto);
		when(moduleServiceMock.findAllModules()).thenReturn(moduleDtoList);
		mockMvc.perform(get("/module/findAll"))
				.andExpect(status().isOk());
	}

	@Test
	public void listEmptyAllModules() throws Exception{
		when(moduleServiceMock.findAllModules()).thenReturn(new ArrayList<>());
		mockMvc.perform(get("/module/findAll"))
				.andExpect(status().isNoContent());
	}

	@Test
	public void getModule() throws Exception{
		when(moduleServiceMock.findById(10)).thenReturn(moduleDto);
		mockMvc.perform(get("/module/{id}", 10))
				.andExpect(status().isOk());
	}

	@Test
	public void getEmptyModule() throws Exception{
		when(moduleServiceMock.findById(10)).thenThrow(new NullPointerException());
		mockMvc.perform(get("/module/{id}", 10))
				.andExpect(status().isNotFound());
	}

	@Test
	public void updateModule() throws Exception{
		when(moduleServiceMock.findById(10)).thenReturn(moduleDto);
		doNothing().when(moduleServiceMock).updateModule(moduleDto);
		mockMvc.perform(put("/module/{id}", 10)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(moduleDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
	}

	@Test
	public void updateNullModule() throws Exception{
		when(moduleServiceMock.findById(0)).thenReturn(null);
		mockMvc.perform(put("/module/{id}", 0)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(moduleDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound());
	}


	@Test
	public void updateWrongModule() throws Exception{
		when(moduleServiceMock.findById(10)).thenReturn(moduleDto);
		moduleDto.setCourse(null);
		doNothing().doThrow(new NullPointerException()).when(moduleServiceMock).updateModule(moduleDto);
		mockMvc.perform(put("/module/{id}", 10)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(moduleDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void deleteModule() throws Exception{
		when(moduleServiceMock.findById(10)).thenReturn(moduleDto);
		doNothing().when(moduleServiceMock).deleteModuleById(10);
		mockMvc.perform(delete("/module/{id}", 10))
				.andExpect(status().isNoContent());
	}

	@Test
	public void deleteNullModule() throws Exception{
		when(moduleServiceMock.findById(0)).thenReturn(null);
		doNothing().when(moduleServiceMock).deleteModuleById(0);
		mockMvc.perform(delete("/module/{id}", 0))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void listAllCourseSModules() throws Exception{
		List<ModuleDto> moduleDtoList = new ArrayList<>();
		for(int i = 0; i < 5; i++) moduleDtoList.add(moduleDto);
		when(moduleServiceMock.findAllCourseSModule(10)).thenReturn(moduleDtoList);
		mockMvc.perform(get("/module/findAll/{id}", 10))
				.andExpect(status().isOk());
	}

	@Test
	public void listAllEmptyCourseSModules() throws Exception{
		when(moduleServiceMock.findAllCourseSModule(10)).thenReturn(new ArrayList<>());
		mockMvc.perform(get("/module/findAll/{id}", 10))
				.andExpect(status().isNotFound());
	}

	@Test
	public void listAllProfessorSModules() throws Exception{
		List<ModuleDto> moduleDtoList = new ArrayList<>();
		for(int i = 0; i < 5; i++) moduleDtoList.add(moduleDto);
		when(moduleServiceMock.findAllProfessorSModule(10)).thenReturn(moduleDtoList);
		mockMvc.perform(get("/module/findByProf/{id}", 10))
				.andExpect(status().isOk());
	}

	@Test
	public void listAllEmptyProfessorSModules() throws Exception{
		when(moduleServiceMock.findAllProfessorSModule(10)).thenReturn(new ArrayList<>());
		mockMvc.perform(get("/module/findByProf/{id}", 10))
				.andExpect(status().isNotFound());
	}

	@Test
	public void listAllCourseSModulesPerYear() throws Exception{
		List<ModuleDto> moduleDtoList = new ArrayList<>();
		for(int i = 0; i < 5; i++) moduleDtoList.add(moduleDto);
		ModuleDto moduleDto2 = new ModuleDto(moduleDto);
		moduleDto2.setYear(2);
		moduleDtoList.add(moduleDto2);
		when(moduleServiceMock.findAllCourseSModule(10)).thenReturn(moduleDtoList);
		mockMvc.perform(get("/module/findAllCoursePerYear/{id}/{year}", 10, 1))
				.andExpect(status().isOk());
	}

	@Test
	public void listAllEmptyCourseSModulesPerYear() throws Exception{
		when(moduleServiceMock.findAllCourseSModule(10)).thenReturn(new ArrayList<>());
		mockMvc.perform(get("/module/findAllCoursePerYear/{id}/{year}", 10, 1))
				.andExpect(status().isNoContent());
	}


}
