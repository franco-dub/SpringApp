package it.unisalento.se.saw.restapi;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.unisalento.se.saw.IService.TicketIService;
import it.unisalento.se.saw.dto.PersonDto;
import it.unisalento.se.saw.dto.ProfessorDto;
import it.unisalento.se.saw.dto.RoomDto;
import it.unisalento.se.saw.dto.StudentDto;
import it.unisalento.se.saw.dto.TicketDto;

@RunWith(MockitoJUnitRunner.class)
public class TicketControllerTest {
	
	ObjectMapper mapper = new ObjectMapper();
	public static final MediaType APPLICATION_JSON_UTF8 = 
			new MediaType(
					MediaType.APPLICATION_JSON.getType(),
					MediaType.APPLICATION_JSON.getSubtype(),
					Charset.forName("utf8"));
	private MockMvc mockMvc;
	TicketDto ticket = new TicketDto();
	ProfessorDto professor = new ProfessorDto();
	RoomDto room = new RoomDto();
	PersonDto person = new PersonDto();
	
	
	
	@Mock
	private TicketIService ticketServiceMock;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new TicketController(ticketServiceMock)).build();
		person.setAddress("via Milano");
		person.setDateOfBirth(new Date(89, 5, 20));
		person.setEmail("email@email.it");
		person.setFirstName("Franco");
		person.setGender("M");
		person.setLastName("Savino");
		person.setPassword("password");
		person.setPersonId(1);
		person.setPhone("5555555555");
		room.setCapacity(100);
		room.setLocation("somewhere");
		room.setName("La Stecca!");
		room.setRoomId(1);
		professor.setPerson(person);
		professor.setProfessorId(1);
		professor.setLevel("prima fascia");
		professor.setHireDate(new Date(118, 9, 11));
		ticket.setDate(new Date());
		ticket.setDescription("bla bla bla");
		ticket.setProfessor(professor);
		ticket.setRoom(room);
		ticket.setStatus("PENDING");
		ticket.setTicketId(1);
		ticket.setTitle("rofl");
	}
	
	@Test
	public void getTicketTest() throws Exception{
		
		when(ticketServiceMock.findById(1)).thenReturn(ticket);
		
		mockMvc.perform(get("/ticket/{id}", 1))
		.andExpect(status().isOk())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8));
		
		verify(ticketServiceMock, times(1)).findById(1);
		verifyNoMoreInteractions(ticketServiceMock);
	}

	@Test
	public void getTicketExceptionTest() throws Exception {
		
		when(ticketServiceMock.findById(1)).thenThrow(new NullPointerException("Not Found"));
		
		mockMvc.perform(get("/ticket/{id}", 1))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void createTicketTest() throws Exception {
		
		Mockito.doNothing().when(ticketServiceMock).saveTicket(ticket);
		
	    mockMvc.perform(post("/ticket/add")
	    		.contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ticket)))
        	.andExpect(status().isCreated());
        verify(ticketServiceMock, times(1)).saveTicket(Mockito.isA(TicketDto.class));
	    verifyNoMoreInteractions(ticketServiceMock);
	}
	
	@Test
	public void createTicketExceptionTest() throws Exception {
		
		ticket.setProfessor(null);
		mockMvc.perform(post("/ticket/add")
	    		.contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ticket)))
        		.andExpect(status().isBadRequest());	
	}
	
	@Test
	public void findAllTicketTest() throws Exception {
		
		List<TicketDto> tickets = new ArrayList<>();
		tickets.add(ticket);
		tickets.add(ticket);
		tickets.add(ticket);
		
		when(ticketServiceMock.findAllTickets()).thenReturn(tickets);
		mockMvc.perform(get("/ticket/findAll"))
			.andExpect(status().isOk());
		verify(ticketServiceMock, times(1)).findAllTickets();
		verifyNoMoreInteractions(ticketServiceMock);
	}
	
	@Test
	public void findAllTicketExceptionTest() throws Exception {

		List<TicketDto> tickets = new ArrayList<>();
		
		when(ticketServiceMock.findAllTickets()).thenReturn(tickets);
		mockMvc.perform(get("/ticket/findAll"))
			.andExpect(status().isNoContent());
	}
	
	@Test
	public void getTicketByProfTest() throws Exception {
		
		List<TicketDto> tickets = new ArrayList<>();
		tickets.add(ticket);
		tickets.add(ticket);
		tickets.add(ticket);
		
		when(ticketServiceMock.findByProf(1)).thenReturn(tickets);

		mockMvc.perform(get("/ticket/findByProfId/{id}", 1))
			.andExpect(status().isOk());
		verify(ticketServiceMock, times(1)).findByProf(1);
		verifyNoMoreInteractions(ticketServiceMock);
	}
	
	@Test
	public void getTicketByProfException1Test() throws Exception {
		
		List<TicketDto> tickets = new ArrayList<>();
		
		when(ticketServiceMock.findByProf(1)).thenReturn(tickets);

		mockMvc.perform(get("/ticket/findByProfId/{id}", 1))
			.andExpect(status().isNoContent());
		verify(ticketServiceMock, times(1)).findByProf(1);
		verifyNoMoreInteractions(ticketServiceMock);
	}
	
	@Test
	public void getTicketByProfException2Test() throws Exception {
		
		when(ticketServiceMock.findByProf(1)).thenThrow(new NullPointerException("Not Found!"));

		mockMvc.perform(get("/ticket/findByProfId/{id}", 1))
			.andExpect(status().isNotFound());
		verify(ticketServiceMock, times(1)).findByProf(1);
		verifyNoMoreInteractions(ticketServiceMock);
	}
	
	@Test
	public void updateTicketTest() throws Exception {
		
		when(ticketServiceMock.findById(1)).thenReturn(ticket);
		Mockito.doNothing().when(ticketServiceMock).updateTicket(ticket);
		
		mockMvc.perform(put("/ticket/{id}", 1)
	    	.contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(ticket)))
        	.andExpect(status().isOk());
		
		verify(ticketServiceMock, times(1)).findById(1);
        verify(ticketServiceMock, times(1)).updateTicket(Mockito.isA(TicketDto.class));
	    verifyNoMoreInteractions(ticketServiceMock);
	}
	
	@Test
	public void updateTicketException1Test() throws Exception {
		
		ticket.setProfessor(null);
		when(ticketServiceMock.findById(1)).thenReturn(ticket);
		Mockito.doNothing().when(ticketServiceMock).updateTicket(ticket);
		
		mockMvc.perform(put("/ticket/{id}", 1)
		    	.contentType(MediaType.APPLICATION_JSON)
	            .content(mapper.writeValueAsString(ticket)))
	        	.andExpect(status().isBadRequest());
	}
	
	@Test
	public void updateTicketException2Test() throws Exception {
		
		when(ticketServiceMock.findById(1)).thenThrow(new NullPointerException("Not found"));
		Mockito.doNothing().when(ticketServiceMock).updateTicket(ticket);
		
		mockMvc.perform(put("/ticket/{id}", 1)
		    	.contentType(MediaType.APPLICATION_JSON)
	            .content(mapper.writeValueAsString(ticket)))
	        	.andExpect(status().isNotFound());
	}
	
	@Test
	public void deleteTicketTest() throws Exception {
		
		when(ticketServiceMock.findById(1)).thenReturn(ticket);
		mockMvc.perform(delete("/ticket/{id}", 1))
    		.andExpect(status().isNoContent());
		
		verify(ticketServiceMock, times(1)).findById(1);
        verify(ticketServiceMock, times(1)).deleteTicketById(1);
	    verifyNoMoreInteractions(ticketServiceMock);
	}
	
	@Test
	public void deleteTicketExceptionTest() throws Exception {
		
		when(ticketServiceMock.findById(1)).thenThrow(new NullPointerException("Not Found"));
		mockMvc.perform(delete("/ticket/{id}", 1))
    		.andExpect(status().isNotFound());
		
		verify(ticketServiceMock, times(1)).findById(1);
	    verifyNoMoreInteractions(ticketServiceMock);
	}
}
