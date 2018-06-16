package it.digitalgarage.marketplace.commons.be.validator.model;

public abstract class ValidationModelCampo {
    
    
    protected String nomeCampo;
    protected String tipoCampo;
    protected String descrizione;
    protected String regex;
    protected Boolean required;
    protected Boolean disabled;
    protected Boolean editabled;
    protected String tooltip;
    protected String valoreDefault;
    protected Integer maxLength;


    public String getNomeCampo() {
        return nomeCampo;
    }

    public void setNomeCampo(String nomeCampo) {
        this.nomeCampo = nomeCampo;
    }

    public String getTipoCampo() {
        return tipoCampo;
    }

    public void setTipoCampo(String tipoCampo) {
        this.tipoCampo = tipoCampo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Boolean getEditabled() {
        return editabled;
    }

    public void setEditabled(Boolean editabled) {
        this.editabled = editabled;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public String getValoreDefault() {
        return valoreDefault;
    }

    public void setValoreDefault(String valoreDefault) {
        this.valoreDefault = valoreDefault;
    }
    
    public Integer getMaxLength() {
		return maxLength;
	}
    
    public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}
}
