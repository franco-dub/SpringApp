package it.unisalento.se.saw.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unisalento.se.saw.IService.PersonIService;
import it.unisalento.se.saw.dto.PersonDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class PersonControllerTest{

	private MockMvc mockMvc;

	@Mock
	PersonIService personServiceMock;

	private PersonDto personDto = new PersonDto();

	private ObjectMapper mapper = new ObjectMapper();


	@Before
	public void setUp(){
		mockMvc = MockMvcBuilders
				.standaloneSetup(new PersonController(personServiceMock))
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

	}

	@Test
	public void createPersonTest() throws Exception{
		when(personServiceMock.savePerson(personDto)).thenReturn(personDto);
		mockMvc.perform(post("/person/add")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(personDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated());
	}

	@Test
	public void createNullPersonTest() throws Exception{
		personDto.setFirstName(null);
		when(personServiceMock.savePerson(personDto)).thenThrow(new NullPointerException());
		mockMvc.perform(post("/person/add")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(personDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void listAllPersons() throws Exception{
		List<PersonDto> personDtoList = new ArrayList<>();
		for(int i = 0; i < 5; i++) personDtoList.add(personDto);
		when(personServiceMock.findAllPersons()).thenReturn(personDtoList);
		mockMvc.perform(get("/person/findAll"))
				.andExpect(status().isOk());
	}

	@Test
	public void listEmptyAllPersons() throws Exception{
		when(personServiceMock.findAllPersons()).thenReturn(new ArrayList<>());
		mockMvc.perform(get("/person/findAll"))
				.andExpect(status().isNoContent());
	}

	@Test
	public void getPerson() throws Exception{
		when(personServiceMock.findById(10)).thenReturn(personDto);
		mockMvc.perform(get("/person/{id}", 10))
				.andExpect(status().isOk());
	}

	@Test
	public void getEmptyPerson() throws Exception{
		when(personServiceMock.findById(10)).thenThrow(new NullPointerException());
		mockMvc.perform(get("/person/{id}", 10))
				.andExpect(status().isNotFound());
	}

	@Test
	public void updatePerson() throws Exception{
		when(personServiceMock.findById(10)).thenReturn(personDto);
		doNothing().when(personServiceMock).updatePerson(personDto, personDto.getPersonId());
		mockMvc.perform(put("/person/{id}", 10)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(personDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
	}

	@Test
	public void updateNullPerson() throws Exception{
		when(personServiceMock.findById(0)).thenReturn(null);
		mockMvc.perform(put("/person/{id}", 0)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(personDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound());
	}


	@Test
	public void updateWrongPerson() throws Exception{
		when(personServiceMock.findById(10)).thenReturn(personDto);
		personDto.setFirstName(null);
		doNothing().doThrow(new NullPointerException()).when(personServiceMock).
				updatePerson(personDto, personDto.getPersonId());
		mockMvc.perform(put("/person/{id}", 10)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(personDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void deletePerson() throws Exception{
		when(personServiceMock.findById(10)).thenReturn(personDto);
		doNothing().when(personServiceMock).deletePersonById(10);
		mockMvc.perform(delete("/person/{id}", 10))
				.andExpect(status().isNoContent());
	}

	@Test
	public void deleteNullPerson() throws Exception{
		when(personServiceMock.findById(0)).thenReturn(null);
		doNothing().when(personServiceMock).deletePersonById(0);
		mockMvc.perform(delete("/person/{id}", 0))
				.andExpect(status().isNotFound());
	}

	@Test
	public void deleteAllPersons() throws Exception{
		doNothing().when(personServiceMock).deleteAllPersons();
		mockMvc.perform(delete("/person/deleteAll"))
				.andExpect(status().isNoContent());
	}
}
