package it.digitalgarage.marketplace.commons.be.dao;

import java.util.HashMap;
import java.util.Map;

public class QueryMap {
	
	Map<String,String> querys = new HashMap<>();
	
	
	public QueryMap(Map<String,String> querys) {
		this.querys=querys;
	}
	
	
	public String get(String queryName){
		return querys.get(queryName);
	}

}
