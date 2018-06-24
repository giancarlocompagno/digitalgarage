package it.example.service;

import it.example.dto.UtenteDTO;

public interface GestioneUtenteBE {
	
	public UtenteDTO load(String username);

}
