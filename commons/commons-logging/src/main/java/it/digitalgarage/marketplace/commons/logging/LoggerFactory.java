package it.digitalgarage.marketplace.commons.logging;

public class LoggerFactory {
	
	private static String subsystemId;
	
	public static void register(String subsystemId){
		LoggerFactory.subsystemId = subsystemId;
	}
	
	public static Logger getLogger(Class<?> clazz){
		return new LoggerImpl(org.slf4j.LoggerFactory.getLogger(clazz),LoggerFactory.subsystemId);
	}
	
	public static Logger getLogger(Class<?> clazz,String subsystemId){
		return new LoggerImpl(org.slf4j.LoggerFactory.getLogger(clazz),subsystemId);
	}
	

}
