package examplemagazzino;

import java.math.BigDecimal;

public class Sconto implements ModalitaDiCalcolo {

	@Override
	public BigDecimal calcola(BigDecimal importo) {
		return importo.multiply(new BigDecimal("0.10"));
	}

}
