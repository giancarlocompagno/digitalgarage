package examplemagazzino;

import java.math.BigDecimal;

public class None implements ModalitaDiCalcolo {

	@Override
	public BigDecimal calcola(BigDecimal importo) {
		return importo;
	}

}
