package it.unisalento.se.saw.IService;

import java.util.List;

import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.exceptions.UserNotFoundException;

public interface IUserService {
	public List<User> getAll();
	public User save(User user) ;
	public User getById(int id) throws UserNotFoundException;
	public void removeUserById(int id) throws UserNotFoundException;
}
