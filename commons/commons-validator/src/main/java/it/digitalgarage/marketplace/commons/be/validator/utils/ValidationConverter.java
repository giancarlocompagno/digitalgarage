package it.digitalgarage.marketplace.commons.be.validator.utils;

import java.util.List;

import it.digitalgarage.marketplace.commons.be.validator.model.ValidationModelConverter;

public interface ValidationConverter {
    ValidationModelConverter converterListToValidate(List<Object> listEntita);
}
