package examplemagazzino;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Ordine {
	
	private List<VoceOrdine> vociOrdine = new ArrayList<>();
	
	public Ordine() {
		// TODO Auto-generated constructor stub
	}
	
	//SI
	public BigDecimal calcolaImporto(){
		BigDecimal importo = new BigDecimal("0");
		for(VoceOrdine v : vociOrdine){
			importo = importo.add(v.calcolaImporto());
		}
		return importo;
	}
	
	//NO
	public BigDecimal calcolaImporto2(){
		BigDecimal importo = new BigDecimal("0");
		for(VoceOrdine v : vociOrdine){
			BigDecimal parziale = v.getMerce().getPrezzo();
			importo = importo.add(parziale.multiply(new BigDecimal(""+v.getNumeroOggetti())));
		}
		return importo;
	}

}
