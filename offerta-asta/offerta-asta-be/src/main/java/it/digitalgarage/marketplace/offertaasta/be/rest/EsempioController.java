package it.digitalgarage.marketplace.offertaasta.be.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
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
