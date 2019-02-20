
package it.unisalento.se.saw.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unisalento.se.saw.IService.SchedulingIService;
import it.unisalento.se.saw.builder.CalendarLessonType;
import it.unisalento.se.saw.dto.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class SchedulingControllerTest{

	private MockMvc mockMvc;

	private CalendarDto calendarDto = new CalendarDto();
	private ModuleDto moduleDto = new ModuleDto();
	private RoomDto roomDto = new RoomDto();
	private StudentDto studentDto = new StudentDto();
	private PersonDto personDto = new PersonDto();
	private CourseDto courseDto = new CourseDto();
	private ProfessorDto professorDto = new ProfessorDto();

	private ObjectMapper mapper = new ObjectMapper();

	@Mock
	private SchedulingIService schedulingServiceMock;

	@Before
	public void setUp(){
		mockMvc = MockMvcBuilders
				.standaloneSetup(new SchedulingController(schedulingServiceMock))
				.build();


		calendarDto.setCalendarId(10);
		CalendarLessonType type = new CalendarLessonType();
		calendarDto.setCalendarDate(type.calendarDate(
				new Date(),
				new Date(),
				new Date(),
				type.type(), null, null));
		calendarDto.setDay("LUNEDI");

		roomDto.setName("test");
		roomDto.setRoomId(1);
		roomDto.setCapacity(10);
		calendarDto.setRoom(roomDto);
		moduleDto.setModuleId(1);
		moduleDto.setCredits(6);
		moduleDto.setYear(1);
		moduleDto.setTitle("testModule");
		moduleDto.setSemester("1");
		calendarDto.setModule(moduleDto);



		personDto.setFirstName("Andrea");
		personDto.setLastName("Chezzinoinoino");
		personDto.setPersonId(70);
		personDto.setEmail("ciao");
		personDto.setPassword("franco");
		personDto.setPhone("phone");
		personDto.setAddress("2000-01-01");
		personDto.setGender("m");

		professorDto.setProfessorId(12);
		professorDto.setPerson(personDto);
		professorDto.setHireDate(new Date());

		courseDto.setName("Il Grande Corso");
		courseDto.setCourseId(1);
		moduleDto.setCourse(courseDto);
		moduleDto.setProfessor(professorDto);

		studentDto.setStudentId(12);
		studentDto.setPerson(personDto);
		studentDto.setRegistrationDate(new Date());
		studentDto.setCourse(courseDto);
	}


/*	@Test
	public void findFreeRooms() throws Exception{
		List<RoomDto> roomDtos = new ArrayList<>();
		for(int i = 0; i < 5; i++) roomDtos.add(roomDto);
		System.out.println(roomDtos);
		when(schedulingServiceMock.findFreeRooms(calendarDto)).thenReturn(roomDtos);
		mockMvc.perform(post("/scheduling/findFreeRooms")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(calendarDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isOk());
	}*/


	@Test
	public void findEmptyFreeRooms() throws Exception{
		when(schedulingServiceMock.findFreeRooms(calendarDto)).thenReturn(new ArrayList<>());
		mockMvc.perform(post("/scheduling/findFreeRooms")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(calendarDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound());
	}

	@Test
	public void findWrongFreeRooms() throws Exception{
		calendarDto.setModule(null);
		when(schedulingServiceMock.findFreeRooms(calendarDto)).thenThrow(new NullPointerException());
		mockMvc.perform(post("/scheduling/findFreeRooms")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(calendarDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest());
	}


	@Test
	public void createCalendars() throws Exception{
		doNothing().when(schedulingServiceMock).saveAllCalendars(calendarDto);
		mockMvc.perform(post("/scheduling/addLectures")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(calendarDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated());
	}

	@Test
	public void createWrongCalendars() throws Exception{
		calendarDto.setModule(null);
		doNothing().doThrow(new NullPointerException()).when(schedulingServiceMock).saveAllCalendars(calendarDto);
		mockMvc.perform(post("/scheduling/addLectures")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(calendarDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest());
	}


}

