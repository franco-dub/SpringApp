package it.unisalento.se.saw.IService;

import java.util.List;

import it.unisalento.se.saw.dto.TicketDto;

public interface TicketIService {

	public TicketDto findById(Integer id);
	public void saveTicket(TicketDto ticketDto);
	public void updateTicket(TicketDto ticketDto);
	public void deleteTicketById(Integer id);
	public List<TicketDto> findAllTickets();
}
