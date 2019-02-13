package it.unisalento.se.saw.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unisalento.se.saw.IService.ModuleIService;
import it.unisalento.se.saw.IService.ProfessorIService;
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
public class ProfessorControllerTest{

	private MockMvc mockMvc;

	@Mock
	ProfessorIService professorServiceMock;

	@Mock
	ModuleIService moduleServiceMock;

	private ProfessorDto professorDto = new ProfessorDto();
	private PersonDto personDto = new PersonDto();
	private ModuleDto moduleDto = new ModuleDto();
	private CourseDto courseDto = new CourseDto();

	private ObjectMapper mapper = new ObjectMapper();


	@Before
	public void setUp(){
		mockMvc = MockMvcBuilders
				.standaloneSetup(new ProfessorController(professorServiceMock, moduleServiceMock))
				.build();

		personDto.setFirstName("Andrea");
		personDto.setLastName("Chezzinoinoino");
		personDto.setPersonId(70);
		personDto.setEmail("ciao");
		personDto.setPassword("franco");
		personDto.setPhone("phone");
		personDto.setAddress("2000-01-01");
		personDto.setGender("m");
		personDto.setDateOfBirth(new Date());

		professorDto.setProfessorId(1);
		professorDto.setHireDate(new Date());
		professorDto.setLevel("first");
		professorDto.setPerson(personDto);

		courseDto.setName("Il Grande Corso");
		courseDto.setCourseId(1);

		moduleDto.setModuleId(1);
		moduleDto.setCredits(6);
		moduleDto.setYear(1);
		moduleDto.setTitle("testModule");
		moduleDto.setSemester("1");
		moduleDto.setCourse(courseDto);
		moduleDto.setProfessor(professorDto);

	}

	@Test
	public void createProfessorTest() throws Exception{
		when(professorServiceMock.saveProfessor(professorDto)).thenReturn(professorDto);
		mockMvc.perform(post("/professor/add")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(professorDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated());
	}

	@Test
	public void createNullProfessorTest() throws Exception{
		professorDto.setPerson(null);
		when(professorServiceMock.saveProfessor(professorDto)).thenThrow(new NullPointerException());
		mockMvc.perform(post("/professor/add")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(professorDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void listAllProfessors() throws Exception{
		List<ProfessorDto> professorDtoList = new ArrayList<>();
		for(int i = 0; i < 5; i++) professorDtoList.add(professorDto);
		when(professorServiceMock.findAllProfessors()).thenReturn(professorDtoList);
		mockMvc.perform(get("/professor/findAll"))
				.andExpect(status().isOk());
	}

	@Test
	public void listEmptyAllProfessors() throws Exception{
		when(professorServiceMock.findAllProfessors()).thenReturn(new ArrayList<>());
		mockMvc.perform(get("/professor/findAll"))
				.andExpect(status().isNoContent());
	}

	@Test
	public void getProfessor() throws Exception{
		when(professorServiceMock.findById(10)).thenReturn(professorDto);
		mockMvc.perform(get("/professor/{id}", 10))
				.andExpect(status().isOk());
	}

	@Test
	public void getEmptyProfessor() throws Exception{
		when(professorServiceMock.findById(10)).thenReturn(null);
		mockMvc.perform(get("/professor/{id}", 10))
				.andExpect(status().isNotFound());
	}

	@Test
	public void updateProfessor() throws Exception{
		when(professorServiceMock.findById(10)).thenReturn(professorDto);
		doNothing().when(professorServiceMock).updateProfessor(professorDto);
		mockMvc.perform(put("/professor/{id}", 10)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(professorDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
	}

	@Test
	public void updateNullProfessor() throws Exception{
		when(professorServiceMock.findById(0)).thenReturn(null);
		mockMvc.perform(put("/professor/{id}", 0)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(professorDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound());
	}


	@Test
	public void updateWrongProfessor() throws Exception{
		professorDto.setPerson(null);
		when(professorServiceMock.findById(10)).thenReturn(professorDto);
		doNothing().doThrow(new NullPointerException()).when(professorServiceMock).
				updateProfessor(professorDto);
		mockMvc.perform(put("/professor/{id}", 10)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(professorDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void deleteProfessor() throws Exception{
		when(professorServiceMock.findById(10)).thenReturn(professorDto);
		doNothing().when(professorServiceMock).deleteProfessorById(10);
		mockMvc.perform(delete("/professor/{id}", 10))
				.andExpect(status().isNoContent());
	}

	@Test
	public void deleteNullProfessor() throws Exception{
		when(professorServiceMock.findById(0)).thenReturn(null);
		doNothing().when(professorServiceMock).deleteProfessorById(0);
		mockMvc.perform(delete("/professor/{id}", 0))
				.andExpect(status().isNotFound());
	}

	@Test
	public void findModuleByProfessorId() throws Exception{
		when(moduleServiceMock.findByProfessorProfessorId(10)).thenReturn(moduleDto);
		mockMvc.perform(get("/professor/findModuleByProfessorId/{id}", 10))
				.andExpect(status().isOk());
	}

	@Test
	public void findEmptyModuleByProfessorId() throws Exception{
		when(moduleServiceMock.findByProfessorProfessorId(10)).thenReturn(null);
		mockMvc.perform(get("/professor/findModuleByProfessorId/{id}", 10))
				.andExpect(status().isNotFound());
	}

}
