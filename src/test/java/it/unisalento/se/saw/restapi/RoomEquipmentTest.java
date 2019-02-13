package it.unisalento.se.saw.restapi;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.ArrayList;
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

import it.unisalento.se.saw.IService.RoomEquipmentIService;
import it.unisalento.se.saw.dto.EquipmentDto;
import it.unisalento.se.saw.dto.RoomDto;
import it.unisalento.se.saw.dto.RoomEquipmentDto;
import it.unisalento.se.saw.dto.SecretaryDto;

@RunWith(MockitoJUnitRunner.class)
public class RoomEquipmentTest {

	ObjectMapper mapper = new ObjectMapper();
	public static final MediaType APPLICATION_JSON_UTF8 = 
			new MediaType(
					MediaType.APPLICATION_JSON.getType(),
					MediaType.APPLICATION_JSON.getSubtype(),
					Charset.forName("utf8"));
	private MockMvc mockMvc;

	@Mock
	private RoomEquipmentIService roomEquipmentServiceMock;
	EquipmentDto equipment1 = new EquipmentDto();
	EquipmentDto equipment2 = new EquipmentDto();
	RoomDto room = new RoomDto();
	RoomEquipmentDto roomEquipment = new RoomEquipmentDto();
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new RoomEquipmentController(roomEquipmentServiceMock)).build();
		
		room.setCapacity(100);
		room.setLocation("asd");
		room.setName("La Stecca");
		room.setRoomId(1);
		equipment1.setEquipmentId(1);
		equipment1.setName("lavagna");
		equipment2.setEquipmentId(2);
		equipment2.setName("proiettore");
		roomEquipment.setEquipment(equipment1);
		roomEquipment.setEquipment(equipment2);
		roomEquipment.setRoom(room);
		roomEquipment.setRoomEquipmentId(1);
		roomEquipment.setWork((byte)1);
	}
	
	@Test
	public void getRoomRquipmentTest() throws Exception{
		
		when(roomEquipmentServiceMock.findById(1)).thenReturn(roomEquipment);
		
		mockMvc.perform(get("/roomEquipment/{id}", 1))
		.andExpect(status().isOk())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8));
		
		verify(roomEquipmentServiceMock, times(1)).findById(1);
		verifyNoMoreInteractions(roomEquipmentServiceMock);
	}
	
	@Test
	public void getRoomRquipmentExceptionTest() throws Exception{
		
		when(roomEquipmentServiceMock.findById(1)).thenThrow(new NullPointerException());
		
		mockMvc.perform(get("/roomEquipment/{id}", 1))
		.andExpect(status().isNotFound())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8));
		
		verify(roomEquipmentServiceMock, times(1)).findById(1);
		verifyNoMoreInteractions(roomEquipmentServiceMock);
	}
	
	@Test
	public void createRoomEquipmentTest() throws Exception {
		
		List<RoomEquipmentDto> roomEquipments = new ArrayList<>();
		roomEquipments.add(roomEquipment);
		roomEquipments.add(roomEquipment);
		roomEquipments.add(roomEquipment);
		
		when(roomEquipmentServiceMock.saveRoomEquipment(roomEquipment)).thenReturn(roomEquipment);
		mockMvc.perform(post("/roomEquipment/add")
	    		.contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(roomEquipments)))
        	.andExpect(status().isCreated());
        verify(roomEquipmentServiceMock, times(3)).saveRoomEquipment(Mockito.isA(RoomEquipmentDto.class));
	    verifyNoMoreInteractions(roomEquipmentServiceMock);
	}
	
	@Test
	public void listAllRoomEquipmentTest() throws Exception {
		
		List<RoomEquipmentDto> roomEquipments = new ArrayList<>();
		roomEquipments.add(roomEquipment);
		roomEquipments.add(roomEquipment);
		roomEquipments.add(roomEquipment);
		
		when(roomEquipmentServiceMock.findAllRoomEquipments()).thenReturn(roomEquipments);
		
		mockMvc.perform(get("/roomEquipment/findAll"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8));
		
		verify(roomEquipmentServiceMock, times(1)).findAllRoomEquipments();
		verifyNoMoreInteractions(roomEquipmentServiceMock);
		
	}
	
	@Test
	public void listAllRoomEquipmentExceptionTest() throws Exception {
		
		List<RoomEquipmentDto> roomEquipments = new ArrayList<>();
		
		when(roomEquipmentServiceMock.findAllRoomEquipments()).thenReturn(roomEquipments);
		
		mockMvc.perform(get("/roomEquipment/findAll"))
		.andExpect(status().isNoContent())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8));
		
		verify(roomEquipmentServiceMock, times(1)).findAllRoomEquipments();
		verifyNoMoreInteractions(roomEquipmentServiceMock);
		
	}
	

	@Test
	public void findAllRoomEquipmentByRoomIdTest() throws Exception {
		
		List<RoomEquipmentDto> roomEquipments = new ArrayList<>();
		roomEquipments.add(roomEquipment);
		roomEquipments.add(roomEquipment);
		roomEquipments.add(roomEquipment);
		
		when(roomEquipmentServiceMock.findRoomEquipmentsByroomId(1)).thenReturn(roomEquipments);
		
		mockMvc.perform(get("/roomEquipment/findByRoomId/{id}", 1))
		.andExpect(status().isOk())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8));
		
		verify(roomEquipmentServiceMock, times(1)).findRoomEquipmentsByroomId(1);
		verifyNoMoreInteractions(roomEquipmentServiceMock);
	}
	
	@Test
	public void findAllRoomEquipmentByRoomIdExceptionTest() throws Exception {

		when(roomEquipmentServiceMock.findRoomEquipmentsByroomId(1)).thenThrow(new NullPointerException());
		
		mockMvc.perform(get("/roomEquipment/findByRoomId/{id}", 1))
		.andExpect(status().isNotFound())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8));
		
		verify(roomEquipmentServiceMock, times(1)).findRoomEquipmentsByroomId(1);
		verifyNoMoreInteractions(roomEquipmentServiceMock);
	}
	
	@Test
	public void updateRoomEquipmentTest() throws Exception {
		
		when(roomEquipmentServiceMock.findById(1)).thenReturn(roomEquipment);
		when(roomEquipmentServiceMock.updateRoomEquipment(roomEquipment)).thenReturn(roomEquipment);
		
		
	    mockMvc.perform(put("/roomEquipment/updateById/{id}", 1)
	    		.contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(roomEquipment)))
        	.andExpect(status().isOk());
	    
	    verify(roomEquipmentServiceMock, times(1)).findById(1);
        verify(roomEquipmentServiceMock, times(1)).updateRoomEquipment(Mockito.isA(RoomEquipmentDto.class));
	    verifyNoMoreInteractions(roomEquipmentServiceMock);
	}
	
	@Test
	public void updateRoomEquipmentException1Test() throws Exception {
		
		roomEquipment.setRoom(null);
		when(roomEquipmentServiceMock.findById(1)).thenReturn(roomEquipment);
		when(roomEquipmentServiceMock.updateRoomEquipment(roomEquipment)).thenReturn(roomEquipment);
		
		
	    mockMvc.perform(put("/roomEquipment/updateById/{id}", 1)
	    		.contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(roomEquipment)))
        	.andExpect(status().isBadRequest());
	    
	    verifyNoMoreInteractions(roomEquipmentServiceMock);
	}
	
	@Test
	public void updateRoomEquipmentException2Test() throws Exception {
		
		when(roomEquipmentServiceMock.findById(1)).thenThrow(new NullPointerException());
		
	    mockMvc.perform(put("/roomEquipment/updateById/{id}", 1)
	    		.contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(roomEquipment)))
        	.andExpect(status().isNotFound());
	    
	    verify(roomEquipmentServiceMock, times(1)).findById(1);
	    verifyNoMoreInteractions(roomEquipmentServiceMock);
	}
	
	@Test
	public void updateRoomEquipmentByRoomTest() throws Exception {
		
		List<RoomEquipmentDto> roomEquipments = new ArrayList<>();
		roomEquipments.add(roomEquipment);
		roomEquipments.add(roomEquipment);
		roomEquipments.add(roomEquipment);
		
		when(roomEquipmentServiceMock.updateRoomEquipment(roomEquipment)).thenReturn(roomEquipment);
		
		
	    mockMvc.perform(post("/roomEquipment/updateByRoom")
	    		.contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(roomEquipments)))
        	.andExpect(status().isOk());
	    
        verify(roomEquipmentServiceMock, times(3)).updateRoomEquipment(Mockito.isA(RoomEquipmentDto.class));
	    verifyNoMoreInteractions(roomEquipmentServiceMock);
	}
	
	@Test
	public void deleteRoomEquipmentTest() throws Exception {

		when(roomEquipmentServiceMock.findById(1)).thenReturn(roomEquipment);
		mockMvc.perform(delete("/roomEquipment/{id}", 1))
			.andExpect(status().isNoContent());
	}

	@Test
	public void deleteRoomEquipmentExceptionTest() throws Exception {

		when(roomEquipmentServiceMock.findById(1)).thenThrow(new NullPointerException());
		mockMvc.perform(delete("/roomEquipment/{id}", 1))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void listAllRoomSEquipmentsTest() throws Exception {
		List<RoomEquipmentDto> roomEquipments = new ArrayList<>();
		roomEquipments.add(roomEquipment);
		roomEquipments.add(roomEquipment);
		roomEquipments.add(roomEquipment);
		
		when(roomEquipmentServiceMock.findAllRoomSEquipments(1)).thenReturn(roomEquipments);
		
		mockMvc.perform(get("/roomEquipment/findAllRoomEquipment/{id}", 1))
		.andExpect(status().isOk())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8));
		
		verify(roomEquipmentServiceMock, times(1)).findAllRoomSEquipments(1);
		verifyNoMoreInteractions(roomEquipmentServiceMock);
		
	}
	
	@Test
	public void listAllRoomSEquipmentsExceptionTest() throws Exception {
		List<RoomEquipmentDto> roomEquipments = new ArrayList<>();
		
		when(roomEquipmentServiceMock.findAllRoomSEquipments(1)).thenReturn(roomEquipments);
		
		mockMvc.perform(get("/roomEquipment/findAllRoomEquipment/{id}", 1))
		.andExpect(status().isNoContent())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8));
		
		verify(roomEquipmentServiceMock, times(1)).findAllRoomSEquipments(1);
		verifyNoMoreInteractions(roomEquipmentServiceMock);
		
	}
	
	@Test
	public void listAllRoomsWhereEquipmentTest() throws Exception {
		List<RoomEquipmentDto> roomEquipments = new ArrayList<>();
		roomEquipments.add(roomEquipment);
		roomEquipments.add(roomEquipment);
		roomEquipments.add(roomEquipment);
		
		when(roomEquipmentServiceMock.findAllRoomsWhereEquipments(1)).thenReturn(roomEquipments);
		
		mockMvc.perform(get("/roomEquipment/findAllRoomWhereEquipment/{id}", 1))
		.andExpect(status().isOk())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8));
		
		verify(roomEquipmentServiceMock, times(1)).findAllRoomsWhereEquipments(1);
		verifyNoMoreInteractions(roomEquipmentServiceMock);
		
	}
	
	@Test
	public void listAllRoomsWhereEquipmentExceptionTest() throws Exception {
		List<RoomEquipmentDto> roomEquipments = new ArrayList<>();
		
		when(roomEquipmentServiceMock.findAllRoomsWhereEquipments(1)).thenReturn(roomEquipments);
		
		mockMvc.perform(get("/roomEquipment/findAllRoomWhereEquipment/{id}", 1))
		.andExpect(status().isNoContent())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8));
		
		verify(roomEquipmentServiceMock, times(1)).findAllRoomsWhereEquipments(1);
		verifyNoMoreInteractions(roomEquipmentServiceMock);
		
	}
}
