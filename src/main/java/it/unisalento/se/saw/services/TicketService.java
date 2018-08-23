package it.unisalento.se.saw.services;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
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
	public void saveCourse(TicketDto ticketDto) {
		ticketDto.setDate(new Date());
	}

	@Override
	public void updateTicket(TicketDto ticketDto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteTicketById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<TicketDto> findAllTickets() {
		// TODO Auto-generated method stub
		return null;
	}

}
