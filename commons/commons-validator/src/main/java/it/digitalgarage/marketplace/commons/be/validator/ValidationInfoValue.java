package it.digitalgarage.marketplace.commons.be.validator;

import java.lang.reflect.Field;

public class ValidationInfoValue {

    private ValidationInfoProperty vip;
    private String keyFieldName;
    private String originalFieldName;
    private String repository;
    private boolean ignore;
    private Field field;

    private static final String SUFFISSO_REPOSITORY = "Repository";


    public ValidationInfoValue(ValidationInfoProperty vip, Field f) {
        if(vip!= null) {
            this.vip = vip;
            this.ignore = vip.ignore();
            this.keyFieldName = vip.fieldValue().isEmpty() ? f.getName() : vip.fieldValue();
            this.originalFieldName = f.getName();
            this.repository = vip.repository();
        } else {
            this.ignore = false;
            this.keyFieldName = f.getName();
            this.originalFieldName = f.getName();
            this.repository = "";
        }
    }

    public String getKeyFieldName() {
        return keyFieldName;
    }

    public String getOriginalFieldName(){
        return originalFieldName;
    }

    /*public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }*/

    public String getRepository() {
        return repository;
    }

    /*public void setRepository(String repository) {
        this.repository = repository;
    }*/

    public boolean isIgnore() {
        return ignore;
    }

    /*public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }*/
}
