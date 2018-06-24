package it.example.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.example.dto.UtenteDTO;
import it.example.security.MySecurityContext;
import it.example.service.GestioneUtenteBE;

@RestController
@RequestMapping("/")
public class GestioneUtenteController {
	
	@Autowired
	GestioneUtenteBE service;
	
	@RequestMapping("/{username}")
	@ResponseBody
	public UtenteDTO load(@PathVariable("username") String username){
		String email = MySecurityContext.getEmail();
		System.out.println(email);
		return service.load(username);
	}

}
