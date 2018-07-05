package it.unisalento.se.saw.IService;

import java.util.List;

import it.unisalento.se.saw.domain.Aula;
import it.unisalento.se.saw.exceptions.AulaNotFoundException;

public interface IAulaService {
	public Aula getById(int id) throws AulaNotFoundException;
	public List<Aula> getAll();
	public Aula save(Aula aula);
	public void delete(int id) throws AulaNotFoundException;
	public List<Aula> getByName(String name);
}
