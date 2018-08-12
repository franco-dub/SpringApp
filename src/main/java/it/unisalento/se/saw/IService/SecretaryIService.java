package it.unisalento.se.saw.IService;

import java.util.List;

import it.unisalento.se.saw.domain.Secretary;

public interface SecretaryIService {
	
	public Secretary findById(Integer id);
	public void saveSecretary(Secretary secretary);
	public void updateSecretary(Secretary secretary);
	public void deleteSecretaryById(Integer id);
	public List<Secretary> findAllSecretaries();
	public boolean isSecretaryExist(Secretary secretary);
}
