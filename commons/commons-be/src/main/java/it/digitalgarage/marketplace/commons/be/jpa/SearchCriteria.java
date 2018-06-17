package it.digitalgarage.marketplace.commons.be.jpa;


public class SearchCriteria {
	
	public static enum OPERATION{
		EQUALS,
		NOT_EQUALS,
		GT,//>
		LT,//<
		GTE,//>=
		LTE,//<=
		//BETWEEN("between"),
		LIKE,
		IN,
		NOT_IN,
		EQUALS_IGNORE_CASE,
		IS_NULL,
		IS_NOT_NULL,
		EXISTS,
		NOT_EXISTS;
		
		private OPERATION() {
		}
		
	}
	

	private String key;
	private OPERATION operation;
	private Object value;

	public SearchCriteria(String key, OPERATION operation, Object value) {
		super();
		this.key = key;
		this.operation = operation;
		this.value = value;
	}

	public SearchCriteria(String key, OPERATION operation) {
		super();
		this.key = key;
		this.operation = operation;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public OPERATION getOperation() {
		return operation;
	}

	public void setOperation(OPERATION operation) {
		this.operation = operation;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}    

}
