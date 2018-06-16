package it.digitalgarage.marketplace.offertaasta.be.signature.dto;

import it.digitalgarage.marketplace.commons.model.ASearchDTO;

public class TipoAstaSearchDTO extends ASearchDTO<String>{

	private String id;

	private String descrizione;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	
	
}
