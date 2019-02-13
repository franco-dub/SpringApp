package it.unisalento.se.saw.restapi;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import it.unisalento.se.saw.IService.SecretaryIService;
import it.unisalento.se.saw.dto.PersonDto;
import it.unisalento.se.saw.dto.SecretaryDto;
import it.unisalento.se.saw.dto.StudentDto;

@RunWith(MockitoJUnitRunner.class)
public class SecretaryControllerTest {
	
	ObjectMapper mapper = new ObjectMapper();
	public static final MediaType APPLICATION_JSON_UTF8 = 
			new MediaType(
					MediaType.APPLICATION_JSON.getType(),
					MediaType.APPLICATION_JSON.getSubtype(),
					Charset.forName("utf8"));
	private MockMvc mockMvc;

	@Mock
	private SecretaryIService secretaryServiceMock;
	PersonDto person = new PersonDto();
	SecretaryDto secretary = new SecretaryDto();
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new SecretaryController(secretaryServiceMock)).build();
		
		person.setAddress("via Milano");
		person.setDateOfBirth(new Date(89, 5, 20));
		person.setEmail("email@email.it");
		person.setFirstName("Franco");
		person.setGender("M");
		person.setLastName("Savino");
		person.setPassword("password");
		person.setPersonId(1);
		person.setPhone("5555555555");
		secretary.setPerson(person);
		secretary.setHireDate(new Date());
		secretary.setSecretaryId(1);
		secretary.setTask("segreteria");
	}
	
	@Test
	public void getSecretaryTest() throws Exception{
		
		when(secretaryServiceMock.findById(1)).thenReturn(secretary);
		
		mockMvc.perform(get("/secretary/{id}", 1))
		.andExpect(status().isOk())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8));
		
		verify(secretaryServiceMock, times(1)).findById(1);
		verifyNoMoreInteractions(secretaryServiceMock);
	}

	@Test
	public void getSecretaryExceptionTest() throws Exception{
		
		when(secretaryServiceMock.findById(1)).thenThrow(new NullPointerException("Not Found"));
		
		mockMvc.perform(get("/secretary/{id}", 1))
		.andExpect(status().isNotFound())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8));
		
		verify(secretaryServiceMock, times(1)).findById(1);
		verifyNoMoreInteractions(secretaryServiceMock);
	}
	
	@Test
	public void createSecretaryTest() throws Exception {
		
		Mockito.doNothing().when(secretaryServiceMock).saveSecretary(secretary);
		
		mockMvc.perform(post("/secretary/add")
	    		.contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(secretary)))
        	.andExpect(status().isCreated());
        verify(secretaryServiceMock, times(1)).saveSecretary(Mockito.isA(SecretaryDto.class));
	    verifyNoMoreInteractions(secretaryServiceMock);
	}
	
	@Test
	public void createSecretaryExceptionTest() throws Exception {
		
		secretary.setPerson(null);
		secretary.setSecretaryId(null);
		
		mockMvc.perform(post("/secretary/add")
	    		.contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(secretary)))
        	.andExpect(status().isBadRequest());
	}
	
	@Test
	public void listAllSecretaryTest() throws Exception {
		
		List<SecretaryDto> secretaries= new ArrayList<>();
		secretaries.add(secretary);
		secretaries.add(secretary);
		secretaries.add(secretary);
		
		when(secretaryServiceMock.findAllSecretaries()).thenReturn(secretaries);
		
		mockMvc.perform(get("/secretary/findAll"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8));
		
		verify(secretaryServiceMock, times(1)).findAllSecretaries();
		verifyNoMoreInteractions(secretaryServiceMock);
	}
	
	@Test
	public void listAllSecretaryExceptionTest() throws Exception {
		
		List<SecretaryDto> secretaries= new ArrayList<>();
		
		when(secretaryServiceMock.findAllSecretaries()).thenReturn(secretaries);
		
		mockMvc.perform(get("/secretary/findAll"))
		.andExpect(status().isNoContent())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8));
		
		verify(secretaryServiceMock, times(1)).findAllSecretaries();
		verifyNoMoreInteractions(secretaryServiceMock);
	}
	
	@Test
	public void deleteSecretaryTest() throws Exception {
		
		when(secretaryServiceMock.findById(1)).thenReturn(secretary);
		mockMvc.perform(delete("/secretary/{id}", 1))
    	.andExpect(status().isNoContent());
	}
	
	@Test
	public void deleteSecretaryExceptionTest() throws Exception {
		
		when(secretaryServiceMock.findById(1)).thenThrow(new NullPointerException("Not Found"));
		mockMvc.perform(delete("/secretary/{id}", 1))
    	.andExpect(status().isNotFound());
	}
	
	@Test
	public void updateSecretaryTest() throws Exception {
		
		when(secretaryServiceMock.findById(1)).thenReturn(secretary);
		Mockito.doNothing().when(secretaryServiceMock).updateSecretary(secretary);
		
	    mockMvc.perform(put("/secretary/{id}", 1)
	    		.contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(secretary)))
        	.andExpect(status().isOk());
	    
	    verify(secretaryServiceMock, times(1)).findById(1);
        verify(secretaryServiceMock, times(1)).updateSecretary(Mockito.isA(SecretaryDto.class));
	    verifyNoMoreInteractions(secretaryServiceMock);
	}
	
	@Test
	public void updateSecretaryException1Test() throws Exception {
		
		
		when(secretaryServiceMock.findById(1)).thenReturn(secretary);
		secretary.setPerson(null);
		Mockito.doNothing().when(secretaryServiceMock).updateSecretary(secretary);
		
	    mockMvc.perform(put("/secretary/{id}", 1)
	    		.contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(secretary)))
        	.andExpect(status().isBadRequest());
	    
	    verifyNoMoreInteractions(secretaryServiceMock);
	}
	
	@Test
	public void updateSecretaryException2Test() throws Exception {
		
		
		when(secretaryServiceMock.findById(1)).thenThrow(new NullPointerException());
		
	    mockMvc.perform(put("/secretary/{id}", 1)
	    		.contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(secretary)))
        	.andExpect(status().isNotFound());
	    
	    verify(secretaryServiceMock, times(1)).findById(1);
	    verifyNoMoreInteractions(secretaryServiceMock);
	}
}
