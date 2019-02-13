package it.unisalento.se.saw.restapi;

import java.nio.charset.Charset;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.unisalento.se.saw.IService.TeachingMaterialIService;

@RunWith(MockitoJUnitRunner.class)
public class TeachingMaterialControllerTest {

	ObjectMapper mapper = new ObjectMapper();
	public static final MediaType APPLICATION_JSON_UTF8 = 
			new MediaType(
					MediaType.APPLICATION_JSON.getType(),
					MediaType.APPLICATION_JSON.getSubtype(),
					Charset.forName("utf8"));
	private MockMvc mockMvc;

	@Mock
	private TeachingMaterialIService tmServiceMock;
}
