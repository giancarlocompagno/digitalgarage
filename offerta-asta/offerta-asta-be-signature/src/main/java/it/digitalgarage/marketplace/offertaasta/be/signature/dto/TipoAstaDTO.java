package it.digitalgarage.marketplace.offertaasta.be.signature.dto;

import it.digitalgarage.marketplace.commons.model.DTO;

public class TipoAstaDTO extends DTO<String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String descrizione;

	public TipoAstaDTO() {
	}
	
	public TipoAstaDTO(String id,String descrizione) {
		this.id = id;
		this.descrizione = descrizione;
	}

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
