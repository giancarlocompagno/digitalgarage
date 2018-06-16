package it.digitalgarage.marketplace.commons.be.jpa;

import javax.persistence.AttributeConverter;

// TODO: Auto-generated Javadoc
/**
 * The Class UpperCaseConverter.
 */
public class UpperCaseConverter implements AttributeConverter<String,String>{
	
	/* (non-Javadoc)
	 * @see javax.persistence.AttributeConverter#convertToDatabaseColumn(java.lang.Object)
	 */
	@Override
	public String convertToDatabaseColumn(String attribute) {
		return attribute!=null?attribute.toUpperCase():null;
	}
	
	
	/* (non-Javadoc)
	 * @see javax.persistence.AttributeConverter#convertToEntityAttribute(java.lang.Object)
	 */
	@Override
	public String convertToEntityAttribute(String dbData) {
		return dbData!=null?dbData.toUpperCase():dbData;
	}
	

}
