package it.unisalento.se.saw.restapi;


import com.fasterxml.jackson.databind.ObjectMapper;
import it.unisalento.se.saw.IService.LectureRatingIService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class LectureRatingControllerTest{

	private MockMvc mockMvc;

	@Mock
	LectureRatingIService lectureRatingServiceMock;


	private ObjectMapper mapper = new ObjectMapper();

	
	private LectureRatingDto lectureRatingDto = new LectureRatingDto();
	private CalendarDto calendarDto = new CalendarDto();
	private StudentDto studentDto = new StudentDto();
	private ModuleDto moduleDto = new ModuleDto();
	private PersonDto personDto = new PersonDto();
	private RoomDto roomDto = new RoomDto();

	@Before
	public void setUp(){
		mockMvc = MockMvcBuilders
				.standaloneSetup(new LectureRatingController(lectureRatingServiceMock))
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


		studentDto.setStudentId(12);
		studentDto.setPerson(personDto);
		studentDto.setRegistrationDate(new Date());

		lectureRatingDto.setCalendar(calendarDto);
		lectureRatingDto.setStudent(studentDto);
		lectureRatingDto.setDate(new Date());
		lectureRatingDto.setLectureRatingId(1);
		lectureRatingDto.setNote("Ciao Franco");
		lectureRatingDto.setRate("Ciao rating");
	}
	

	@Test
	public void createLectureRatingTest() throws Exception{
		doNothing().when(lectureRatingServiceMock).saveLectureRating(lectureRatingDto);
		mockMvc.perform(post("/lectureRating/add")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(lectureRatingDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated());
	}

	@Test
	public void createNullLectureRatingTest() throws Exception{
		lectureRatingDto.setStudent(null);
		doNothing().doThrow(new NullPointerException()).when(lectureRatingServiceMock).saveLectureRating(lectureRatingDto);
		mockMvc.perform(post("/lectureRating/add")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(lectureRatingDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void listAllLectureRatings() throws Exception{
		List<LectureRatingDto> lectureRatingDtoList = new ArrayList<>();
		for(int i = 0; i < 5; i++) lectureRatingDtoList.add(lectureRatingDto);
		when(lectureRatingServiceMock.findAll()).thenReturn(lectureRatingDtoList);
		mockMvc.perform(get("/lectureRating/findAll"))
				.andExpect(status().isOk());
	}

	@Test
	public void listEmptyAllLectureRatings() throws Exception{
		when(lectureRatingServiceMock.findAll()).thenReturn(new ArrayList<>());
		mockMvc.perform(get("/lectureRating/findAll"))
				.andExpect(status().isNoContent());
	}

	@Test
	public void getLectureRating() throws Exception{
		when(lectureRatingServiceMock.findById(10)).thenReturn(lectureRatingDto);
		mockMvc.perform(get("/lectureRating/{id}", 10))
				.andExpect(status().isOk());
	}

	@Test
	public void getEmptyLectureRating() throws Exception{
		when(lectureRatingServiceMock.findById(10)).thenReturn(null);
		mockMvc.perform(get("/lectureRating/{id}", 10))
				.andExpect(status().isNotFound());
	}


	@Test
	public void getLectureRatingByLecture() throws Exception{
		List<LectureRatingDto> lectureRatingDtoList = new ArrayList<>();
		for(int i = 0; i < 5; i++) lectureRatingDtoList.add(lectureRatingDto);
		when(lectureRatingServiceMock.findAllBylecture(10)).thenReturn(lectureRatingDtoList);
		mockMvc.perform(get("/lectureRating/findByModule/{id}", 10))
				.andExpect(status().isOk());

	}

	@Test
	public void getEmptyLectureRatingByLecture() throws Exception{
		when(lectureRatingServiceMock.findAllBylecture(10)).thenReturn(new ArrayList<>());
		mockMvc.perform(get("/lectureRating/findByModule/{id}", 10))
				.andExpect(status().isNotFound());

	}

	@Test
	public void getLectureRatingByStudentAndLecture() throws Exception{
		when(lectureRatingServiceMock.findByStudentIdAndLectureId(10, 1)).thenReturn(lectureRatingDto);
		mockMvc.perform(get("/lectureRating/findByStudentAndLecture/{studentId}/{calendarId}", 10, 1))
				.andExpect(status().isOk());

	}

	@Test
	public void getEmptyLectureRatingByStudentAndLecture() throws Exception{
		when(lectureRatingServiceMock.findByStudentIdAndLectureId(10, 1)).thenReturn(null);
		mockMvc.perform(get("/lectureRating/findByStudentAndLecture/{studentId}/{calendarId}", 10, 1))
				.andExpect(status().isNotFound());

	}
	
}
