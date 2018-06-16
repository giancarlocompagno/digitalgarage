package it.digitalgarage.marketplace.commons.be.validator.model;

import java.util.HashMap;
import java.util.Map;

public abstract class ValidationModelConverter {

    protected Map<String,ValidationModelEntita> validationModelMap = new HashMap<>();


    public Map<String, ValidationModelEntita> getValidationModelMap() {
        return validationModelMap;
    }

    public void addValidationModelMap(ValidationModelEntita vme) {
        this.validationModelMap.put(vme.getCodiceEntita(),vme);
    }
}
