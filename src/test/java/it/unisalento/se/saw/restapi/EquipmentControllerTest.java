package it.unisalento.se.saw.restapi;


import com.fasterxml.jackson.databind.ObjectMapper;
import it.unisalento.se.saw.IService.EquipmentIService;
import it.unisalento.se.saw.dto.EquipmentDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class EquipmentControllerTest{

	private MockMvc mockMvc;

	@Mock
	EquipmentIService equipmentServiceMock;
	
	private EquipmentDto equipmentDto = new EquipmentDto();

	private ObjectMapper mapper = new ObjectMapper();


	@Before
	public void setUp(){
		mockMvc = MockMvcBuilders
				.standaloneSetup(new EquipmentController(equipmentServiceMock))
				.build();

		equipmentDto.setName("Il Grande Equipaggiamento");
		equipmentDto.setEquipmentId(1);
	}

	@Test
	public void createEquipmentTest() throws Exception{
		doNothing().when(equipmentServiceMock).saveEquipment(equipmentDto);
		mockMvc.perform(post("/equipment/add")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(equipmentDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated());
	}

	@Test
	public void createNullEquipmentTest() throws Exception{
		equipmentDto.setName(null);
		doNothing().doThrow(new NullPointerException()).when(equipmentServiceMock).saveEquipment(equipmentDto);
		mockMvc.perform(post("/equipment/add")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(equipmentDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void listAllEquipments() throws Exception{
		List<EquipmentDto> equipmentDtoList = new ArrayList<>();
		for(int i = 0; i < 5; i++) equipmentDtoList.add(equipmentDto);
		when(equipmentServiceMock.findAllEquipments()).thenReturn(equipmentDtoList);
		mockMvc.perform(get("/equipment/findAll"))
				.andExpect(status().isOk());
	}

	@Test
	public void listEmptyAllEquipments() throws Exception{
		when(equipmentServiceMock.findAllEquipments()).thenReturn(new ArrayList<>());
		mockMvc.perform(get("/equipment/findAll"))
				.andExpect(status().isNoContent());
	}

	@Test
	public void getEquipment() throws Exception{
		when(equipmentServiceMock.findById(10)).thenReturn(equipmentDto);
		mockMvc.perform(get("/equipment/{id}", 10))
				.andExpect(status().isOk());
	}

	@Test
	public void getEmptyEquipment() throws Exception{
		when(equipmentServiceMock.findById(10)).thenThrow(new NullPointerException());
		mockMvc.perform(get("/equipment/{id}", 10))
				.andExpect(status().isNotFound());
	}

	@Test
	public void updateEquipment() throws Exception{
		when(equipmentServiceMock.findById(10)).thenReturn(equipmentDto);
		doNothing().when(equipmentServiceMock).updateEquipment(equipmentDto);
		mockMvc.perform(put("/equipment/{id}", 10)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(equipmentDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
	}

	@Test
	public void updateNullEquipment() throws Exception{
		when(equipmentServiceMock.findById(0)).thenReturn(null);
		mockMvc.perform(put("/equipment/{id}", 0)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(equipmentDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest());
	}


	@Test
	public void updateWrongEquipment() throws Exception{
		when(equipmentServiceMock.findById(10)).thenReturn(equipmentDto);
		equipmentDto.setName(null);
		doNothing().doThrow(new NullPointerException()).when(equipmentServiceMock).updateEquipment(equipmentDto);
		mockMvc.perform(put("/equipment/{id}", 10)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(equipmentDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound());
	}

	@Test
	public void deleteEquipment() throws Exception{
		when(equipmentServiceMock.findById(10)).thenReturn(equipmentDto);
		doNothing().when(equipmentServiceMock).deleteEquipmentById(10);
		mockMvc.perform(delete("/equipment/{id}", 10))
				.andExpect(status().isNoContent());
	}

	@Test
	public void deleteNullEquipment() throws Exception{
		when(equipmentServiceMock.findById(0)).thenReturn(null);
		doNothing().when(equipmentServiceMock).deleteEquipmentById(0);
		mockMvc.perform(delete("/equipment/{id}", 0))
				.andExpect(status().isNotFound());
	}
	
}
