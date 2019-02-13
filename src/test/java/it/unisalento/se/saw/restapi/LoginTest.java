package it.unisalento.se.saw.restapi;

import com.sun.istack.Nullable;
import it.unisalento.se.saw.IService.PersonIService;
import it.unisalento.se.saw.IService.ProfessorIService;
import it.unisalento.se.saw.IService.SecretaryIService;
import it.unisalento.se.saw.IService.StudentIService;
import it.unisalento.se.saw.dto.PersonDto;
import it.unisalento.se.saw.dto.ProfessorDto;
import it.unisalento.se.saw.dto.SecretaryDto;
import it.unisalento.se.saw.dto.StudentDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@RunWith(MockitoJUnitRunner.class)
public class LoginTest{

	private static final MediaType APPLICATION_JSON_UTF8 =
			new MediaType(
					MediaType.APPLICATION_JSON.getType(),
					MediaType.APPLICATION_JSON.getSubtype(),
					Charset.forName("utf8"));
	private MockMvc mockMvc;

	@Mock
	private PersonIService personServiceMock;
	@Mock
	private ProfessorIService professorService;
	@Mock
	private StudentIService studentService;
	@Mock
	private SecretaryIService secretaryService;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders
				.standaloneSetup(new Login(personServiceMock, professorService, studentService, secretaryService))
				.build();
	}

	/**
	 * Test
	 */
	@Test
	public void logTestStudent() throws Exception{
		String email = "ciaooo@ciao.it";
		String password = "ciaooo";
		PersonDto personDto = new PersonDto();
		personDto.setFirstName("Andrea");
		personDto.setLastName("Chezzinoinoino");
		personDto.setPersonId(70);
		personDto.setEmail(email);
		personDto.setPassword(password);
		personDto.setPhone("phone");
		personDto.setAddress("2000-01-01");
		personDto.setGender("m");
		StudentDto studentDto = new StudentDto();
		studentDto.setStudentId(12);
		studentDto.setPerson(personDto);
		studentDto.setRegistrationDate(new Date());

		test(personDto, studentDto, null, null, "STUDENT");

	}

	@Test
	public void logTestProfessor() throws Exception{
		String email = "ciaooo@ciao.it";
		String password = "ciaooo";
		PersonDto personDto = new PersonDto();
		personDto.setFirstName("Andrea");
		personDto.setLastName("Chezzinoinoino");
		personDto.setPersonId(70);
		personDto.setEmail(email);
		personDto.setPassword(password);
		personDto.setPhone("phone");
		personDto.setAddress("2000-01-01");
		personDto.setGender("m");
		ProfessorDto professorDto = new ProfessorDto();
		professorDto.setProfessorId(12);
		professorDto.setPerson(personDto);
		professorDto.setHireDate(new Date());

		test(personDto, null, professorDto, null, "PROFESSOR");

	}

	@Test
	public void logTestSecretary() throws Exception{
		String email = "ciaooo@ciao.it";
		String password = "ciaooo";
		PersonDto personDto = new PersonDto();
		personDto.setFirstName("Andrea");
		personDto.setLastName("Chezzinoinoino");
		personDto.setPersonId(70);
		personDto.setEmail(email);
		personDto.setPassword(password);
		personDto.setPhone("phone");
		personDto.setAddress("2000-01-01");
		personDto.setGender("m");
		SecretaryDto secretaryDto = new SecretaryDto();
		secretaryDto.setSecretaryId(12);
		secretaryDto.setPerson(personDto);
		secretaryDto.setHireDate(new Date());

		test(personDto, null, null, secretaryDto, "SECRETARY");

	}

	@Test
	public void logTestNull() throws Exception{
		String email = "ciaooo@ciao.it";
		String password = "ciaooo";
		PersonDto personDto = new PersonDto();
		personDto.setFirstName("Andrea");
		personDto.setLastName("Chezzinoinoino");
		personDto.setPersonId(70);
		personDto.setEmail(email);
		personDto.setPassword(password);
		personDto.setPhone("phone");
		personDto.setAddress("2000-01-01");
		personDto.setGender("m");
		test(personDto, null, null, null, "NOT_FOUND");
	}




	private void test(PersonDto personDto,
	                  @Nullable StudentDto studentDto,
	                  @Nullable ProfessorDto professorDto,
	                  @Nullable SecretaryDto secretaryDto,
	                  String value) throws Exception{

		when(personServiceMock.findByEmailAndPassword(personDto.getEmail(), personDto.getPassword()))
				.thenReturn(personDto);
		if(studentDto!=null){
			when(studentService.findByPerson(personDto)).thenReturn(studentDto);
		}else if(professorDto!=null){
			when(professorService.findByPerson(personDto)).thenReturn(professorDto);
		}else {
			when(secretaryService.findByPerson(personDto)).thenReturn(secretaryDto);
		}
		mockMvc.perform(get("/login/{email}/{password}", personDto.getEmail(), personDto.getPassword()))
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(jsonPath("$.type", is(value)));
		verify(personServiceMock, times(1))
				.findByEmailAndPassword(personDto.getEmail(), personDto.getPassword());
		verifyNoMoreInteractions(personServiceMock);
	}


}
