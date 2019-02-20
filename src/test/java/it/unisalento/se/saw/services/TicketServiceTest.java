package it.unisalento.se.saw.services;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import it.unisalento.se.saw.domain.Person;
import it.unisalento.se.saw.domain.Professor;
import it.unisalento.se.saw.domain.Room;
import it.unisalento.se.saw.domain.Ticket;
import it.unisalento.se.saw.domain.TmRating;
import it.unisalento.se.saw.dto.TicketDto;
import it.unisalento.se.saw.dto.TmRatingDto;
import it.unisalento.se.saw.repo.TicketRepository;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceTest {

	@Mock
	private static TicketRepository ticketRepoMock;
	@InjectMocks
	private static TicketService ticketService;
	private static final ModelMapper modelMapper = new ModelMapper();
	Ticket ticket = new Ticket();
	Professor professor = new Professor();
	Room room = new Room();
	Person person = new Person();
	TicketDto ticketDto = new TicketDto();
	
	@Before
	public void before(){
		this.ticketService = new TicketService(ticketRepoMock);
		
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
	public void findByIdTest() throws Exception {
		when(ticketRepoMock.findById(1)).thenReturn(Optional.of(ticket));
		ticketDto = modelMapper.map(ticket, TicketDto.class);
		
		TicketDto retrivedDto = modelMapper.map(ticketService.findById(1), TicketDto.class);
		Assert.assertEquals(ticket.getTitle(), retrivedDto.getTitle());
	}
	
	@Test
	public void saveTicketTest() throws Exception {
		
		when(ticketRepoMock.save(ticket)).thenReturn(ticket);
		ticketDto = modelMapper.map(ticket, TicketDto.class);
		ticketService.saveTicket(ticketDto);
		
	}
	
	@Test
	public void findAllTicketTest() throws Exception {
		
		ticketDto = modelMapper.map(ticket, TicketDto.class);

		List<Ticket> list = new ArrayList<>();
		list.add(ticket);
		list.add(ticket);
		list.add(ticket);
		when(ticketRepoMock.findAll()).thenReturn(list);
		
		List<TicketDto> recListDto = ticketService.findAllTickets();
		Assert.assertEquals(list.get(0).getTitle(), recListDto.get(0).getTitle());
		Assert.assertEquals(list.get(1).getTitle(), recListDto.get(1).getTitle());
		Assert.assertEquals(list.get(2).getTitle(), recListDto.get(2).getTitle());
	}
	
	@Test
	public void updateTicketTest() throws Exception {
		
		when(ticketRepoMock.save(ticket)).thenReturn(ticket);
		ticketDto = modelMapper.map(ticket, TicketDto.class);
		ticketService.updateTicket(ticketDto);
		
	}
	
	@Test
	public void findByProfTest() throws Exception {
		
		ticketDto = modelMapper.map(ticket, TicketDto.class);

		List<Ticket> list = new ArrayList<>();
		list.add(ticket);
		list.add(ticket);
		list.add(ticket);
		when(ticketRepoMock.findByProfessorProfessorId(1)).thenReturn(list);
		
		List<TicketDto> recListDto = ticketService.findByProf(1);
		Assert.assertEquals(list.get(0).getTitle(), recListDto.get(0).getTitle());
		Assert.assertEquals(list.get(1).getTitle(), recListDto.get(1).getTitle());
		Assert.assertEquals(list.get(2).getTitle(), recListDto.get(2).getTitle());
	}
}
