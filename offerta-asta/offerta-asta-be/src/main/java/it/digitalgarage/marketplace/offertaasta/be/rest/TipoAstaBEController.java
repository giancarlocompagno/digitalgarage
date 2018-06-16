package it.digitalgarage.marketplace.offertaasta.be.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.digitalgarage.marketplace.commons.restinvoker.signature.EntityNotFoundException;
import it.digitalgarage.marketplace.commons.restinvoker.signature.NotDeletedException;
import it.digitalgarage.marketplace.commons.restinvoker.signature.NotSavedException;
import it.digitalgarage.marketplace.offertaasta.be.signature.TipoAstaBE;
import it.digitalgarage.marketplace.offertaasta.be.signature.dto.TipoAstaDTO;
import it.digitalgarage.marketplace.offertaasta.be.signature.dto.TipoAstaSearchDTO;

@RestController
@RequestMapping({"/tipoAsta"})
public class TipoAstaBEController implements TipoAstaBE{

	@Autowired
	@Qualifier("tipoAstaBEService")
	TipoAstaBE service;

	@Override
	public TipoAstaDTO load(String id) throws EntityNotFoundException {
		return service.load(id);
	}

	@Override
	public void delete(String id) throws NotDeletedException {
		service.delete(id);
	}

	@Override
	public TipoAstaDTO save(TipoAstaDTO t) throws NotSavedException {
		return service.save(t);
	}

	@Override
	public PageTipoAstaDTO search(TipoAstaSearchDTO example) {
		return service.search(example);
	}

}