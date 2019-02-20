package it.unisalento.se.saw.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import it.unisalento.se.saw.domain.Course;
import it.unisalento.se.saw.domain.Person;
import it.unisalento.se.saw.domain.Student;
import it.unisalento.se.saw.domain.TeachingMaterial;
import it.unisalento.se.saw.domain.TmRating;
import it.unisalento.se.saw.dto.TmRatingDto;
import it.unisalento.se.saw.repo.TmRatingRepository;

@RunWith(MockitoJUnitRunner.class)
public class TmRatingServiceTest {

	@Mock
	private static TmRatingRepository tmRatingRepoMock;
	@InjectMocks
	private static TmRatingService tmRatingService;
	private static final ModelMapper modelMapper = new ModelMapper();

	Person person = new Person();
	TmRating tmRating = new TmRating();
	Student student = new Student();
	TeachingMaterial teachingMaterial = new TeachingMaterial();
	Course course = new Course();
	TmRatingDto tmRatingDto = new TmRatingDto();

	@Before
	public void before(){
		this.tmRatingService = new TmRatingService(tmRatingRepoMock);
		course.setCfu(180);
		course.setCourseId(1);
		course.setCourseType("BACHELOR");
		course.setDescription("no description for me");
		course.setName("Ingegneria");
		course.setYear(3);
		person.setAddress("via Milano");
		person.setDateOfBirth(new Date(89, 5, 20));
		person.setEmail("email@email.it");
		person.setFirstName("Franco");
		person.setGender("M");
		person.setLastName("Savino");
		person.setPassword("password");
		person.setPersonId(1);
		person.setPhone("5555555555");
		student.setCourse(course);
		student.setPerson(person);
		student.setStudentId(1);
		student.setRegistrationDate(new Date(118, 9, 11));
		teachingMaterial.setCreated(new Date());
		teachingMaterial.setFileName("file.pdf");
		teachingMaterial.setFileType("application/pdf");
		teachingMaterial.setSize(23424);
		teachingMaterial.setTeachingMaterialId(1);
		tmRating.setRate("3");
		tmRating.setStudent(student);
		tmRating.setTmRatingId(1);
		tmRating.setTeachingMaterial(teachingMaterial);
	}
	
	@Test
	public void findByIdTest() throws Exception {
		
		when(tmRatingRepoMock.findById(1)).thenReturn(Optional.of(tmRating));
		tmRatingDto = modelMapper.map(tmRating, TmRatingDto.class);
		
		TmRatingDto retrivedDto = modelMapper.map(tmRatingService.findById(1), TmRatingDto.class);
		Assert.assertEquals(tmRatingDto.getTmRatingId(), retrivedDto.getTmRatingId());
	}
	
	@Test
	public void saveTmRatingTest() throws Exception {
		
		when(tmRatingRepoMock.save(tmRating)).thenReturn(tmRating);
		tmRatingDto = modelMapper.map(tmRating, TmRatingDto.class);
		tmRatingService.saveTmRating(tmRatingDto);
		
	}
	
	@Test
	public void findAllTmRatingTest() throws Exception {
		
		tmRatingDto = modelMapper.map(tmRating, TmRatingDto.class);

		List<TmRating> list = new ArrayList<>();
		list.add(tmRating);
		list.add(tmRating);
		list.add(tmRating);
		when(tmRatingRepoMock.findAll()).thenReturn(list);
		
		List<TmRatingDto> recListDto = tmRatingService.findAllTmRatings();
		Assert.assertEquals(list.get(0).getRate(), recListDto.get(0).getRate());
		Assert.assertEquals(list.get(1).getRate(), recListDto.get(1).getRate());
		Assert.assertEquals(list.get(2).getRate(), recListDto.get(2).getRate());
	}
	
	@Test
	public void findByTeachingMaterialTeachingMaterialIdTest() throws Exception {
		
		tmRatingDto = modelMapper.map(tmRating, TmRatingDto.class);

		List<TmRating> list = new ArrayList<>();
		list.add(tmRating);
		list.add(tmRating);
		list.add(tmRating);
		when(tmRatingRepoMock.findByTeachingMaterialTeachingMaterialId(1)).thenReturn(list);
		
		List<TmRatingDto> recListDto = tmRatingService.findByTeachingMaterialTeachingMaterialId(1);
		Assert.assertEquals(list.get(0).getRate(), recListDto.get(0).getRate());
		Assert.assertEquals(list.get(1).getRate(), recListDto.get(1).getRate());
		Assert.assertEquals(list.get(2).getRate(), recListDto.get(2).getRate());
	}
	
	@Test
	public void findByStudentIdAndTeachingMaterialIdTest() throws Exception {
		
		when(tmRatingRepoMock.findByStudentAndTm(1, 1)).thenReturn(tmRating);
		tmRatingDto = modelMapper.map(tmRating, TmRatingDto.class);
		
		TmRatingDto retrivedDto = modelMapper.map(tmRatingService.findByStudentIdAndTeachingMaterialId(1, 1), TmRatingDto.class);
		Assert.assertEquals(tmRatingDto.getTmRatingId(), retrivedDto.getTmRatingId());
	}
}
