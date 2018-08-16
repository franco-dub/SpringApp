package it.unisalento.se.saw.services;
/*
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.IService.IUserService;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.exceptions.UserNotFoundException;
import it.unisalento.se.saw.repositories.UserRepository;

@Service
public class UserService implements IUserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Transactional(readOnly=true)
	public List<User> getAll(){
		return userRepository.findAll();
	}
	
	@Transactional
	public User save(User user) {
		return userRepository.save(user);
	}
	
	public User getById(int id) throws UserNotFoundException{
		try {
			User user = userRepository.getOne(id);
			return user;
		} catch (Exception e) {
			//TODO: handle exception
			throw new UserNotFoundException();
		}
	}
	
	@Transactional(rollbackFor=UserNotFoundException.class)
	public void removeUserById(int id) throws UserNotFoundException{
		try {
			User user = userRepository.getOne(id);
			userRepository.delete(user);
		}catch (Exception e) {
			throw new UserNotFoundException();
		}
	}
}
*/