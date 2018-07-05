package it.unisalento.se.saw.controllers;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import it.unisalento.se.saw.IService.IUserService;
import it.unisalento.se.saw.domain.User;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {
	private MockMvc mockMvc;
	
	@Mock
	private IUserService userService;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new HomeController(userService))
				.setViewResolvers(viewResolver())
				.build();
	}
	
	@Test
	public void getAllUsersTest() throws Exception {
		User first = new User();
		first.setEmail("osvaldo@mail.it");
		first.setName("Osvaldo");
		first.setSurname("Rossi");
		
		User second = new User();
		second.setEmail("mario@mail.it");
		second.setName("Mario");
		second.setSurname("Bianchi");
		
		when(userService.getAll()).thenReturn(Arrays.asList(first, second));
		
		mockMvc.perform(get("/home/users"))
			.andExpect(view().name("users"))
			.andExpect(forwardedUrl("/templates/users.jsp"))
			.andExpect(model().attribute("users", hasSize(2)))
			.andExpect(model().attribute("users", hasItem(allOf(
							hasProperty("email", is("osvaldo@mail.it")),
							hasProperty("name", is("Osvaldo")),
							hasProperty("surname", is("Rossi"))))));
		
		verify(userService, times(1)).getAll();
		verifyNoMoreInteractions(userService);
	}
	
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/templates/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
}
