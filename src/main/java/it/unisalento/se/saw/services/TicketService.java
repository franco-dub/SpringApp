package it.unisalento.se.saw.services;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.IService.TicketIService;
import it.unisalento.se.saw.domain.Ticket;
import it.unisalento.se.saw.dto.TicketDto;
import it.unisalento.se.saw.repo.TicketRepository;

@Service
public class TicketService implements TicketIService {

	private static final ModelMapper modelMapper = new ModelMapper();
	TicketRepository ticketRepository;
	@Autowired
	public TicketService(TicketRepository ticketRepository) {
		super();
		this.ticketRepository = ticketRepository;
	}

	@Override
	@Transactional
	public TicketDto findById(Integer id) {
		Ticket ticket = ticketRepository.findById(id).get();
		return modelMapper.map(ticket, TicketDto.class);
	}
	
	@Override
	@Transactional
	public void saveTicket(TicketDto ticketDto) {
		ticketDto.setDate(new Date());
		ticketDto.setStatus("PENDING");
		Ticket ticket = modelMapper.map(ticketDto, Ticket.class);
		ticketRepository.save(ticket);
	}

	@Override
	@Transactional
	public void updateTicket(TicketDto ticketDto) {
		ticketDto.setLastModified(new Date());
		Ticket ticket = modelMapper.map(ticketDto, Ticket.class);
		ticketRepository.save(ticket);
	}

	@Override
	@Transactional
	public void deleteTicketById(Integer id) {
		ticketRepository.deleteById(id);
	}

	@Override
	@Transactional
	public List<TicketDto> findAllTickets() {
		List<Ticket> tickets = ticketRepository.findAll();
		Type targetListType = new TypeToken<List<TicketDto>>() {}.getType();
		List<TicketDto> ticketDtos = modelMapper.map(tickets, targetListType);
		return ticketDtos;
	}
	
	@Override
	@Transactional
	public List<TicketDto> findByProf(Integer id) {
		List<Ticket> tickets = ticketRepository.findByProfessorProfessorId(id);
		Type targetListType = new TypeToken<List<TicketDto>>() {}.getType();
		List<TicketDto> ticketDtos = modelMapper.map(tickets, targetListType);
		return ticketDtos;
	}


}
