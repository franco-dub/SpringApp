package it.unisalento.se.saw.restapi;

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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.unisalento.se.saw.IService.RoomIService;
import it.unisalento.se.saw.dto.RoomDto;
import it.unisalento.se.saw.dto.RoomEquipmentDto;

@RunWith(MockitoJUnitRunner.class)
public class RoomControllerTest {

	ObjectMapper mapper = new ObjectMapper();
	public static final MediaType APPLICATION_JSON_UTF8 = 
			new MediaType(
					MediaType.APPLICATION_JSON.getType(),
					MediaType.APPLICATION_JSON.getSubtype(),
					Charset.forName("utf8"));
	private MockMvc mockMvc;

	@Mock
	private RoomIService roomServiceMock;
	RoomDto room = new RoomDto();
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new RoomController(roomServiceMock)).build();
		
		room.setCapacity(100);
		room.setLocation("asd");
		room.setName("La Stecca");
		room.setRoomId(1);
	}
	
	@Test
	public void getRoomTest() throws Exception{
		
		when(roomServiceMock.findById(1)).thenReturn(room);
		mockMvc.perform(get("/room/{id}", 1))
		.andExpect(status().isOk())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8));
		
		verify(roomServiceMock, times(1)).findById(1);
		verifyNoMoreInteractions(roomServiceMock);
	}
	
	@Test
	public void getRoomExceptionTest() throws Exception{
		
		when(roomServiceMock.findById(1)).thenThrow(new NullPointerException());
		mockMvc.perform(get("/room/{id}", 1))
		.andExpect(status().isNotFound())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8));
		
		verify(roomServiceMock, times(1)).findById(1);
		verifyNoMoreInteractions(roomServiceMock);
	}
	
	@Test
	public void createRoomTest() throws Exception{
		
		when(roomServiceMock.saveRoom(room)).thenReturn(room);
		
		mockMvc.perform(post("/room/add")
	    		.contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(room)))
        	.andExpect(status().isCreated());
        verify(roomServiceMock, times(1)).saveRoom(Mockito.isA(RoomDto.class));
	    verifyNoMoreInteractions(roomServiceMock);
	}
	
	@Test
	public void createRoomExceptionTest() throws Exception{
		
		room.setName(null);
		when(roomServiceMock.saveRoom(room)).thenReturn(room);
		
		mockMvc.perform(post("/room/add")
	    		.contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(room)))
        	.andExpect(status().isBadRequest());
	    verifyNoMoreInteractions(roomServiceMock);
	}
	
	@Test
	public void listAllRoomTest() throws Exception{
		
		List<RoomDto> rooms = new ArrayList<>();
		rooms.add(room);
		rooms.add(room);
		rooms.add(room);

		when(roomServiceMock.findAllRooms()).thenReturn(rooms);
		mockMvc.perform(get("/room/findAll"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8));
		
		verify(roomServiceMock, times(1)).findAllRooms();
		verifyNoMoreInteractions(roomServiceMock);
	}
	
	@Test
	public void listAllRoomExceptionTest() throws Exception{
		
		List<RoomDto> rooms = new ArrayList<>();

		when(roomServiceMock.findAllRooms()).thenReturn(rooms);
		mockMvc.perform(get("/room/findAll"))
		.andExpect(status().isNoContent())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8));
		
		verify(roomServiceMock, times(1)).findAllRooms();
		verifyNoMoreInteractions(roomServiceMock);
	}
	
	@Test
	public void updateRoomTest() throws Exception{
		
		when(roomServiceMock.findById(1)).thenReturn(room);
		Mockito.doNothing().when(roomServiceMock).updateRoom(room);
		
		mockMvc.perform(post("/room/updateById/{id}", 1)
	    		.contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(room)))
        	.andExpect(status().isOk());
		verify(roomServiceMock, times(1)).findById(1);
        verify(roomServiceMock, times(1)).updateRoom(Mockito.isA(RoomDto.class));
	    verifyNoMoreInteractions(roomServiceMock);
	}
	
	@Test
	public void updateRoomException1Test() throws Exception{
		
		room.setName(null);
		when(roomServiceMock.findById(1)).thenReturn(room);
		Mockito.doNothing().when(roomServiceMock).updateRoom(room);
		
		mockMvc.perform(post("/room/updateById/{id}", 1)
	    		.contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(room)))
        	.andExpect(status().isBadRequest());

	    verifyNoMoreInteractions(roomServiceMock);
	}
	
	@Test
	public void updateRoomException2Test() throws Exception{
		
		when(roomServiceMock.findById(1)).thenThrow(new NullPointerException());
		
		mockMvc.perform(post("/room/updateById/{id}", 1)
	    		.contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(room)))
        	.andExpect(status().isNotFound());
		verify(roomServiceMock, times(1)).findById(1);
	    verifyNoMoreInteractions(roomServiceMock);
	}
	
	@Test
	public void deleteRoomTest() throws Exception{
		
		when(roomServiceMock.findById(1)).thenReturn(room);
		Mockito.doNothing().when(roomServiceMock).deleteRoomById(1);
		
		mockMvc.perform(delete("/room/{id}", 1))
		.andExpect(status().isNoContent());
	}
	
	@Test
	public void deleteRoomExceptionTest() throws Exception{
		
		when(roomServiceMock.findById(1)).thenThrow(new NullPointerException());
		
		mockMvc.perform(delete("/room/{id}", 1))
		.andExpect(status().isNotFound());
	}
}
