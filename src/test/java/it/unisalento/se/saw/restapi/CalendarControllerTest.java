package it.unisalento.se.saw.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unisalento.se.saw.IService.CalendarIService;
import it.unisalento.se.saw.IService.ModuleIService;
import it.unisalento.se.saw.dto.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class CalendarControllerTest{
	private static final MediaType APPLICATION_JSON_UTF8 =
			new MediaType(
					MediaType.APPLICATION_JSON.getType(),
					MediaType.APPLICATION_JSON.getSubtype(),
					Charset.forName("utf8"));
	private MockMvc mockMvc;

	private ObjectMapper mapper = new ObjectMapper();


	private CalendarDto calendarDto = new CalendarDto();
	private ModuleDto moduleDto = new ModuleDto();
	private RoomDto roomDto = new RoomDto();
	private StudentDto studentDto = new StudentDto();
	private PersonDto personDto = new PersonDto();
	private CourseDto courseDto = new CourseDto();
	private ProfessorDto professorDto = new ProfessorDto();


	@Mock
	private CalendarIService calendarServiceMock;
	@Mock
	private ModuleIService moduleServiceMock;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders
				.standaloneSetup(new CalendarController(calendarServiceMock, moduleServiceMock))
				.build();

		calendarDto.setCalendarId(10);
		calendarDto.setStartDate(new Date());
		calendarDto.setDate(new Date());
		calendarDto.setEndDate(new Date());
		calendarDto.setDay("LUNEDI");
		calendarDto.setStartTime(new Date());
		calendarDto.setEndTime(new Date());
		roomDto.setName("test");
		roomDto.setRoomId(1);
		calendarDto.setRoom(roomDto);
		moduleDto.setModuleId(1);
		moduleDto.setCredits(6);
		moduleDto.setYear(1);
		moduleDto.setTitle("testModule");
		moduleDto.setSemester("1");
		calendarDto.setModule(moduleDto);
		calendarDto.setType("Lecture TEst");



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


	@Test
	public void getCalendarTest() throws Exception{

		when(calendarServiceMock.findById(calendarDto.getCalendarId())).thenReturn(calendarDto);
		mockMvc.perform(get("/calendar/{id}", calendarDto.getCalendarId()))
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.calendarId", is(calendarDto.getCalendarId())));

		verify(calendarServiceMock, times(1))
				.findById(calendarDto.getCalendarId());
		verifyNoMoreInteractions(calendarServiceMock);

	}

	@Test
	public void getWrongCalendarTest() throws Exception{
		when(calendarServiceMock.findById(calendarDto.getCalendarId())).thenThrow(new NullPointerException("Not Found"));
		mockMvc.perform(get("/calendar/{id}", calendarDto.getCalendarId()))
				.andExpect(status().isNotFound());
	}

	@Test
	public void listAllCalendarsTest() throws Exception{
		List<CalendarDto> calendarDtos = new ArrayList<>();
		for(int i = 0; i < 10; i++){
			calendarDtos.add(calendarDto);
		}
		when(calendarServiceMock.findAllCalendars()).thenReturn(calendarDtos);
		mockMvc.perform(get("/calendar/findAll"))
				.andExpect(jsonPath("$[0].calendarId", is(10)))
				.andExpect(status().isOk());
		verify(calendarServiceMock, times(1)).findAllCalendars();
		verifyNoMoreInteractions(calendarServiceMock);
	}

	@Test
	public void emptyListAllCalendarsTest() throws Exception{
		List<CalendarDto> calendarDtos = new ArrayList<>();
		when(calendarServiceMock.findAllCalendars()).thenReturn(calendarDtos);
		mockMvc.perform(get("/calendar/findAll"))
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isNoContent());
		verify(calendarServiceMock, times(1)).findAllCalendars();
		verifyNoMoreInteractions(calendarServiceMock);
	}

	@Test
	public void createWrongCalendar() throws Exception{
		calendarDto.setModule(null);
		when(calendarServiceMock.saveCalendar(calendarDto)).thenThrow(new NullPointerException());
		mockMvc.perform(post("/calendar/add")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(calendarDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	public void createCalendar() throws Exception{
		when(calendarServiceMock.saveCalendar(calendarDto)).thenReturn(calendarDto);
		mockMvc.perform(post("/calendar/add")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(calendarDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated());
	}



	@Test
	public void updateCalendar() throws Exception{
		when(calendarServiceMock.findById(calendarDto.getCalendarId())).thenReturn(calendarDto);
		when(calendarServiceMock.updateCalendar(calendarDto)).thenReturn(calendarDto);
		mockMvc.perform(put("/calendar/{id}", calendarDto.getCalendarId())
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(calendarDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
	}

	@Test
	public void updateWrongIDCalendar() throws Exception{
		when(calendarServiceMock.findById(-1)).thenReturn(null);
		when(calendarServiceMock.updateCalendar(calendarDto)).thenReturn(calendarDto);
		mockMvc.perform(put("/calendar/{id}", -1)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(calendarDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound());
	}

	@Test
	public void updateWrongCalendar() throws Exception{
		calendarDto.setModule(null);
		when(calendarServiceMock.findById(calendarDto.getCalendarId())).thenReturn(calendarDto);
		when(calendarServiceMock.updateCalendar(calendarDto)).thenThrow(new NullPointerException("Not Found"));
		mockMvc.perform(put("/calendar/{id}", calendarDto.getCalendarId())
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(calendarDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void deleteCalendar() throws Exception{
		when(calendarServiceMock.findById(calendarDto.getCalendarId())).thenReturn(calendarDto);
		mockMvc.perform(delete("/calendar/{id}", calendarDto.getCalendarId()))
				.andExpect(status().isNoContent());
	}

	@Test
	public void deleteEmptyCalendar() throws Exception{
		when(calendarServiceMock.findById(-1)).thenThrow(new NullPointerException("Not Found"));
		mockMvc.perform(delete("/calendar/{id}", -1))
				.andExpect(status().isNotFound());
	}

	@Test
	public void getStudentCalendar() throws Exception{
		List<ModuleDto> moduleDtos = new ArrayList<>();
		for(int i = 1; i < 5; i++){
			moduleDto.setYear(i);
			moduleDtos.add(moduleDto);
		}
		when(moduleServiceMock.findAllCourseSModule(studentDto.getCourse().getCourseId())).thenReturn(moduleDtos);
		mockMvc.perform(post("/calendar/getStudentCalendar")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(studentDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void getWrongStudentCalendar() throws Exception{
		when(moduleServiceMock.findAllCourseSModule(studentDto.getCourse().getCourseId())).thenThrow(new NullPointerException());
		mockMvc.perform(post("/calendar/getStudentCalendar")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(studentDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound());
	}

	@Test
	public void listAllCalendarsByModule() throws Exception{
		List<CalendarDto> calendarDtos = new ArrayList<>();
		for(int i = 0; i < 5; i++) calendarDtos.add(calendarDto);
		when(calendarServiceMock.findAllCalendarByModule(10)).thenReturn(calendarDtos);
		mockMvc.perform(get("/calendar/findByModuleId/{id}", 10))
				.andExpect(status().isOk());

		verify(calendarServiceMock, times(1)).findAllCalendarByModule(10);
		verifyNoMoreInteractions(calendarServiceMock);
	}


	@Test
	public void listAllWrongCalendarsByModule() throws Exception{
		when(calendarServiceMock.findAllCalendarByModule(10)).thenReturn(new ArrayList<>());
		mockMvc.perform(get("/calendar/findByModuleId/{id}", 10))
				.andExpect(status().isNoContent());
	}

	@Test
	public void listAllProfessorsCalendar() throws Exception{
		List<ModuleDto> moduleDtos = new ArrayList<>();
		List<CalendarDto> calendarDtos = new ArrayList<>();

		for(int i = 1; i < 5; i++){
			moduleDtos.add(moduleDto);
			calendarDtos.add(calendarDto);
		}
		String date = "04-05-1999";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		LocalDate lDate = LocalDate.parse(date, formatter);
		Date dd = Date.from(lDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		when(moduleServiceMock.findAllProfessorSModule(10)).thenReturn(moduleDtos);
		when(calendarServiceMock.findAllCalendarByModuleAndDate(10, dd)).thenReturn(calendarDtos);
		mockMvc.perform(get("/calendar/findByProfessorAndDate/{id}/{date}", 10, date))
				.andExpect(status().isOk());
	}

	@Test
	public void listEmptyAllProfessorsCalendar() throws Exception{

		String date = "04-05-1999";
		when(moduleServiceMock.findAllProfessorSModule(10)).thenReturn(new ArrayList<>());
		mockMvc.perform(get("/calendar/findByProfessorAndDate/{id}/{date}", 10, date))
				.andExpect(status().isNoContent());
	}

	@Test
	public void listAllStudentCalendar() throws Exception{
		List<ModuleDto> moduleDtos = new ArrayList<>();
		List<CalendarDto> calendarDtos = new ArrayList<>();

		for(int i = 1; i < 5; i++){
			moduleDtos.add(moduleDto);
			calendarDtos.add(calendarDto);
		}

		String date = "04-05-1999";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		LocalDate lDate = LocalDate.parse(date, formatter);
		Date dd = Date.from(lDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

		when(moduleServiceMock.findAllCourseSModulePerYear(10,1999)).thenReturn(moduleDtos);
		when(calendarServiceMock.findAllCalendarByModuleAndDate(10, dd)).thenReturn(calendarDtos);
		mockMvc.perform(get("/calendar/findByStudentAndDate/{id}/{year}/{date}", 10, 1999, date))
				.andExpect(status().isOk());
	}


	@Test
	public void listEmptyAllStudentCalendar() throws Exception{

		String date = "04-05-1999";
		when(moduleServiceMock.findAllCourseSModulePerYear(10, 1999)).thenReturn(new ArrayList<>());
		mockMvc.perform(get("/calendar/findByStudentAndDate/{id}/{year}/{date}", 10, 1999, date))
				.andExpect(status().isNoContent());
	}
}
