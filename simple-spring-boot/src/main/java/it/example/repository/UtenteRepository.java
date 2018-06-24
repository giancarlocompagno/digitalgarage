package it.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.example.model.Utente;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, String> {

	List<Utente> findByLastName(String string);
	
}
