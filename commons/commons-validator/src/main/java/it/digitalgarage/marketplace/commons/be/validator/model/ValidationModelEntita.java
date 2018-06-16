package it.digitalgarage.marketplace.commons.be.validator.model;

import java.util.HashMap;
import java.util.Map;

public abstract class ValidationModelEntita {

    protected Map<String,ValidationModelCampo> elencoCampi = new HashMap<>();
    protected String codiceEntita;


    public String getCodiceEntita() {
        return codiceEntita;
    }

    public void setCodiceEntita(String codiceEntita) {
        this.codiceEntita = codiceEntita;
    }

    public void addCampo(ValidationModelCampo vmc) {
        this.elencoCampi.put(vmc.getNomeCampo(),vmc);
    }

    public Map<String, ValidationModelCampo> getElencoCampi() {
        return elencoCampi;
    }

}



