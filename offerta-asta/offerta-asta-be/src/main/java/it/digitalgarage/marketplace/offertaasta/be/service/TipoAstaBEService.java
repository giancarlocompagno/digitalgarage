package it.digitalgarage.marketplace.offertaasta.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.digitalgarage.marketplace.commons.be.jpa.QBEUtils;
import it.digitalgarage.marketplace.commons.be.jpa.QBEUtils.MakeSpecification;
import org.springframework.data.domain.Pageable;
import it.digitalgarage.marketplace.commons.restinvoker.signature.EntityNotFoundException;
import it.digitalgarage.marketplace.commons.restinvoker.signature.NotDeletedException;
import it.digitalgarage.marketplace.commons.restinvoker.signature.NotSavedException;
import it.digitalgarage.marketplace.offertaasta.be.model.TipoAsta;
import it.digitalgarage.marketplace.offertaasta.be.repository.TipoAstaRepository;
import it.digitalgarage.marketplace.offertaasta.be.signature.TipoAstaBE;
import it.digitalgarage.marketplace.offertaasta.be.signature.dto.TipoAstaDTO;
import it.digitalgarage.marketplace.offertaasta.be.signature.dto.TipoAstaSearchDTO;

@Service
@Transactional
public class TipoAstaBEService implements TipoAstaBE {

	
	@Autowired
	TipoAstaRepository tipoAstaRepository;
	
	@Override
	public TipoAstaDTO load(String id) throws EntityNotFoundException {
		TipoAsta tipoAsta =  tipoAstaRepository.getOne(id);
		return convert(tipoAsta);
	}

	@Override
	public void delete(String id) throws NotDeletedException {
		// TODO Auto-generated method stub

	}

	@Override
	public TipoAstaDTO save(TipoAstaDTO t) throws NotSavedException {
		TipoAsta tipoAsta = tipoAstaRepository.findById(t.getId()).orElse(convert(t));
		tipoAsta.setDescrizione(t.getDescrizione());
		tipoAsta = tipoAstaRepository.save(tipoAsta);
		return convert(tipoAsta);
	}

	@Override
	public PageTipoAstaDTO search(TipoAstaSearchDTO example) {
		Pageable pageable = QBEUtils.makePageable(example,(list)->{
			return list;
		});
		//MakeExample<SearchConcessioneDTO, EvoluzioneContratto> make = QBEUtils.MakeExample.to(example, EvoluzioneContratto.class);
		MakeSpecification<TipoAsta> makeSpecification = QBEUtils.makeSpecification(TipoAsta.class);
		makeSpecification
		.equal("codice",example.getId())
		.like("descrizione",example.getDescrizione())
		;
		
		Specification<TipoAsta> spec = makeSpecification.get();
		
		org.springframework.data.domain.Page<TipoAsta> es = tipoAstaRepository.findAll(spec, pageable);
		
		return  new PageTipoAstaDTO(QBEUtils.makePage(es, (entity)->{
			return convert(entity);
		}));
	}
	
	
	private static TipoAsta convert(TipoAstaDTO tipoAstaDTO){
		return new TipoAsta(tipoAstaDTO.getId(),tipoAstaDTO.getDescrizione());
	}
	
	private static TipoAstaDTO convert(TipoAsta tipoAsta){
		return new TipoAstaDTO(tipoAsta.getCodice(),tipoAsta.getDescrizione());
	}

}
