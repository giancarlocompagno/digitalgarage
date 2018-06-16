package it.digitalgarage.marketplace.commons.encryption;

import java.beans.PropertyEditorSupport;

public class EncryptedPropertyEditor extends PropertyEditorSupport {

	
  
  public EncryptedPropertyEditor() {

  }
  
  
  @Override
  public void setAsText(String text) {
    setValue(new EncryptedProperty(text));
  }
  
}