package examplemagazzino;

import java.math.BigDecimal;

public class Iva implements ModalitaDiCalcolo {

	@Override
	public BigDecimal calcola(BigDecimal importo) {
		return importo.multiply(new BigDecimal("1.22"));
	}

}
