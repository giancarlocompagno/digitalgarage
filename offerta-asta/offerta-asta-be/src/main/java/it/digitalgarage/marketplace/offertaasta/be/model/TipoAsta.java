package it.digitalgarage.marketplace.offertaasta.be.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the TIPO_CANONE database table.
 * 
 */
@Entity
@Table(name="TIPO_ASTA")
public class TipoAsta {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="COD_TIPO_ASTA")
	private String codice;

	@Column(name="DESCRIZIONE")
	private String descrizione;

	public TipoAsta() {
	}

	
	
	public TipoAsta(String codice, String descrizione) {
		super();
		this.codice = codice;
		this.descrizione = descrizione;
	}



	public String getCodice() {
		return this.codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}