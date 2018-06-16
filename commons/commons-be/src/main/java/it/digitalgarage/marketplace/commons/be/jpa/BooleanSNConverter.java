package it.digitalgarage.marketplace.commons.be.jpa;

import javax.persistence.AttributeConverter;

// TODO: Auto-generated Javadoc
/**
 * The Class UpperCaseConverter.
 */
public class BooleanSNConverter implements AttributeConverter<Boolean,Character>{
	
	/* (non-Javadoc)
	 * @see javax.persistence.AttributeConverter#convertToDatabaseColumn(java.lang.Object)
	 */
	@Override
	public Character convertToDatabaseColumn(Boolean attribute) {
		return attribute!=null && attribute ? new Character('S') : attribute!=null && !attribute ? new Character('N') : null;
	}
	
	
	/* (non-Javadoc)
	 * @see javax.persistence.AttributeConverter#convertToEntityAttribute(java.lang.Object)
	 */
	@Override
	public Boolean convertToEntityAttribute(Character dbData) {
		return dbData!=null && dbData.charValue()=='S' ? Boolean.TRUE : dbData!=null && dbData.charValue()=='N' ? Boolean.FALSE : null;
	}
	

}
