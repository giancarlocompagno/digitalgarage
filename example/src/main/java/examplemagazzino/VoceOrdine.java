package examplemagazzino;

import java.math.BigDecimal;

public class VoceOrdine {
	
	private int numeroOggetti;
	private Merce merce;
	
	
	public VoceOrdine(Merce merce,int numeroOggetti) {
		this.merce = merce;
		this.numeroOggetti = numeroOggetti;
	}
	
	public Merce getMerce() {
		return merce;
	}
	
	public int getNumeroOggetti() {
		return numeroOggetti;
	}
	
	 public BigDecimal calcolaImporto(){
		 return this.merce.calcolaImporto().multiply(new BigDecimal(""+numeroOggetti));
	 }

}
