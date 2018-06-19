package it.digitalgarage.marketplace.offertaasta.be.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/esempio")
public class EsempioController {
	
	
	@ResponseBody
	@RequestMapping(value="saluta",method=RequestMethod.POST)
	public ResponseEntity<RispostaDTO> saluta(@RequestBody UtenteDTO dto){
		
		if(dto.getNome()!=null){
			return new ResponseEntity<>(new RispostaDTO("ciao "+dto.getNome()),HttpStatus.OK);
		}else{
			return new ResponseEntity<>(new RispostaDTO("utente non creato"),HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
	@ResponseBody
	@RequestMapping(value="saluta/{nome}",method=RequestMethod.GET)
	public ResponseEntity<RispostaDTO> saluta(@PathVariable("nome") String nome){
		
		if(nome!=null){
			return new ResponseEntity<>(new RispostaDTO("sono GET: ciao "+nome),HttpStatus.OK);
		}else{
			return new ResponseEntity<>(new RispostaDTO("sono GET: utente non creato"),HttpStatus.BAD_REQUEST);
		}
		
	}

}
