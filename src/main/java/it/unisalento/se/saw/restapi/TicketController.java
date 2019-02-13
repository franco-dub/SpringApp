package it.unisalento.se.saw.restapi;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import it.unisalento.se.saw.IService.TicketIService;
import it.unisalento.se.saw.dto.TicketDto;
import it.unisalento.se.saw.exceptions.CustomErrorType;

@RestController
@CrossOrigin
@RequestMapping(path = "/ticket")
public class TicketController {

	TicketIService ticketService;
	@Autowired
	public TicketController(TicketIService ticketService) {
		super();
		this.ticketService = ticketService;
	}

	// -------------------Create a Ticket-------------------------------------------

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<?> createTicket(@Valid @RequestBody TicketDto ticketDto, BindingResult brs) {
		if (!brs.hasErrors()) {
			ticketService.saveTicket(ticketDto);
			return new ResponseEntity<TicketDto>(ticketDto, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(new CustomErrorType("Unable to create new Ticket. "),
				HttpStatus.BAD_REQUEST);
	}

	//-------------------Retrieve All Tickets--------------------------------------------------------

	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	public ResponseEntity<?> listAllTickets() {
		List<TicketDto> ticketDtos = ticketService.findAllTickets();
		if (ticketDtos.isEmpty()) {
			return new ResponseEntity<>(new CustomErrorType("List empty."),
					HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
			//NO_CONTENT doesn't print json error
		}
		return new ResponseEntity<List<TicketDto>>(ticketDtos, HttpStatus.OK);
	}


	// -------------------Retrieve Tickets by Professor------------------------------------------

	@RequestMapping(value = "findByProfId/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getTicketByProf(@PathVariable("id") int id) {
		try {
			List<TicketDto> ticketDtos = ticketService.findByProf(id);
			if (ticketDtos.isEmpty()) {
				return new ResponseEntity<>(new CustomErrorType("List empty."),
						HttpStatus.NO_CONTENT);
				// You many decide to return HttpStatus.NOT_FOUND
				//NO_CONTENT doesn't print json error
			}
			return new ResponseEntity<List<TicketDto>>(ticketDtos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new CustomErrorType("Professor with id " + id 
					+ " not found" + e.toString()), HttpStatus.NOT_FOUND);
		}
	}

	// -------------------Retrieve Single Ticket------------------------------------------

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getTicket(@PathVariable("id") int id) {
		try {
			TicketDto ticketDto = ticketService.findById(id);
			return new ResponseEntity<TicketDto>(ticketDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new CustomErrorType("Ticket with id " + id 
					+ " not found" + e.toString()), HttpStatus.NOT_FOUND);
		}
	}


	// ------------------- Update a Ticket ------------------------------------------------

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateTicket(@PathVariable("id") int id, 
			@Valid @RequestBody TicketDto ticketDto, BindingResult brs) {
		if (!brs.hasErrors()) {
			try {
				ticketDto.setDate(ticketService.findById(id).getDate());
				ticketDto.setTicketId(id);
			} catch( Exception e) {
				return new ResponseEntity<>(new CustomErrorType("Unable to update. Ticket with id " 
						+ id + " not found. " + e.toString()), HttpStatus.NOT_FOUND);
			}
			ticketService.updateTicket(ticketDto);
			return new ResponseEntity<TicketDto>(ticketDto, HttpStatus.OK);
		}
		return new ResponseEntity<>(new CustomErrorType("Unable to create new Ticket."),
				HttpStatus.BAD_REQUEST);
	}

	//------------------- Delete a Ticket --------------------------------------------------------

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteTicket(@PathVariable("id") Integer id) {
		System.out.println("Fetching & Deleting Ticket with id " + id);
		try {
			ticketService.findById(id);
		} catch (Exception e) {
			return new ResponseEntity<>(new CustomErrorType("Unable to delete! Ticket with id " + id 
					+ " not found. "), HttpStatus.NOT_FOUND);
		}
		ticketService.deleteTicketById(id);
		return new ResponseEntity<TicketDto>(HttpStatus.NO_CONTENT);

	}
}
