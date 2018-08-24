package it.unisalento.se.saw.restapi;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.se.saw.IService.TicketIService;
import it.unisalento.se.saw.dto.TicketDto;
import it.unisalento.se.saw.exceptions.CustomErrorType;

@RestController
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
    public ResponseEntity<?> createTicket(@Valid @RequestBody TicketDto ticketDto) {
    	try {
    		ticketService.saveTicket(ticketDto);
            return new ResponseEntity<TicketDto>(ticketDto, HttpStatus.CREATED);
    	} catch(Exception e)
    	{
    		return new ResponseEntity<>(new CustomErrorType("Unable to create new Ticket. " + e.toString()),
    				HttpStatus.BAD_REQUEST);
    	}
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
    
// -------------------Retrieve Single Equipment------------------------------------------
    
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
    		@Valid @RequestBody TicketDto ticketDto) {
    	try {
    		ticketDto.setDate(ticketService.findById(id).getDate());
    		try {
    			ticketDto.setTicketId(id);
    			ticketService.updateTicket(ticketDto);
                return new ResponseEntity<TicketDto>(ticketDto, HttpStatus.OK);
    		} catch (Exception e) {
    			return new ResponseEntity<>(new CustomErrorType("Unable to create new Ticket. " + e.getMessage()),
        				HttpStatus.BAD_REQUEST);
    		}
    	} catch( Exception e) {
    		return new ResponseEntity<>(new CustomErrorType("Unable to update. Ticket with id " 
            		+ id + " not found. " + e.toString()), HttpStatus.NOT_FOUND);
    	}
    }
    
//------------------- Delete a Ticket --------------------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTicket(@PathVariable("id") Integer id) {
    	System.out.println("Fetching & Deleting Ticket with id " + id);
        try {
        	ticketService.findById(id);
        	ticketService.deleteTicketById(id);
            return new ResponseEntity<TicketDto>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
        	return new ResponseEntity<>(new CustomErrorType("Unable to delete! Ticket with id " + id 
                    + " not found. " + e.toString()), HttpStatus.NOT_FOUND);
        }
    }
	
}
