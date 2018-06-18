package examplemagazzino;

import java.math.BigDecimal;

public class Merce {
	
	private String nome;
	private BigDecimal prezzo;
	private ModalitaDiCalcolo modalitaCalcolo;
	
	public Merce(String nome,BigDecimal prezzo,ModalitaDiCalcolo modalidaDiCalcolo) {
		this.nome = nome;
		this.prezzo = prezzo;
		this.modalitaCalcolo = modalidaDiCalcolo;
	}
	
	public Merce(String nome,BigDecimal prezzo){
		this(nome, prezzo, new None());
	}
	
	
	public BigDecimal getPrezzo() {
		return prezzo;
	}
	
	public BigDecimal calcolaImporto(){
		return modalitaCalcolo.calcola(this.prezzo);
	}

}
