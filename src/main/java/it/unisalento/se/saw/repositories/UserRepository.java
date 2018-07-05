package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query("select u from User u where u.userType.iduserType=1 and u.corsoDiStudio.idcorsoDiStudio=:idCorsoDiStudio")
	public List<User> getUserByCorsoDiStudioId(@Param("idCorsoDiStudio")int idCorsoDiStudio);
	
	List<User> findByName(@Param("name") String name);
}
