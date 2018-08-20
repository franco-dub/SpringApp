package it.unisalento.se.saw.IService;

import java.util.List;

import it.unisalento.se.saw.dto.PersonDto;
import it.unisalento.se.saw.dto.SecretaryDto;

public interface SecretaryIService {
	
	public SecretaryDto findById(Integer id);
	public void saveSecretary(SecretaryDto secretaryDto);
	public void updateSecretary(SecretaryDto secretaryDto);
	public void deleteSecretaryById(Integer id);
	public List<SecretaryDto> findAllSecretaries();
	public SecretaryDto findByPerson(PersonDto personDto);
}
