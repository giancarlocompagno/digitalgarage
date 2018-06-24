package it.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.example.dto.UtenteDTO;
import it.example.model.Utente;
import it.example.repository.UtenteRepository;
import it.example.service.GestioneUtenteBE;

@Service
@Transactional
public class GestioneUtenteBEService implements GestioneUtenteBE {
	
	@Autowired
	UtenteRepository utenteRepository;

	@Override
	public UtenteDTO load(String username) {
		Utente utente = utenteRepository.findById(username).orElse(new Utente(username,null,null));
		return new UtenteDTO(utente.getUsername(),utente.getFirstName(),utente.getLastName());
	}

}
