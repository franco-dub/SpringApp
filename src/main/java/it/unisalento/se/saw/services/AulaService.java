package it.unisalento.se.saw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.IService.IAulaService;
import it.unisalento.se.saw.domain.Aula;
import it.unisalento.se.saw.exceptions.AulaNotFoundException;
import it.unisalento.se.saw.repositories.AulaRepository;

@Service
public class AulaService implements IAulaService {

	@Autowired
	AulaRepository aulaRepository;
	
	@Override
	@Transactional
	public Aula getById(int id) throws AulaNotFoundException {
		// TODO Auto-generated method stub
		try {
			Aula aula = aulaRepository.getOne(id);
			return aula;
		} catch (Exception e) {
			throw new AulaNotFoundException();
		}
	}

	@Override
	@Transactional
	public List<Aula> getAll() {
		// TODO Auto-generated method stub
		return aulaRepository.findAll();
	}

	@Override
	@Transactional
	public Aula save(Aula aula) {
		// TODO Auto-generated method stub
		return aulaRepository.save(aula);
	}

	@Override
	@Transactional
	public void delete(int id) throws AulaNotFoundException {
		// TODO Auto-generated method stub
		try {
			Aula aula = aulaRepository.getOne(id);
			aulaRepository.delete(aula);
		} catch (Exception e) {
			throw new AulaNotFoundException();
		}

	}

	@Override
	@Transactional
	public List<Aula> getByName(String name) {
		// TODO Auto-generated method stub
		/*
		List<Aula> list = aulaRepository.getByName(name);
		if (list == null) {
			return new ArrayList<Aula>();
		}*/
		return null;
	}

}
