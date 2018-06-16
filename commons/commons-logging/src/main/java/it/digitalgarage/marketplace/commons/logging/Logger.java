package it.digitalgarage.marketplace.commons.logging;

public interface Logger {

	String getName();

	boolean isDebugEnabled();

	boolean isErrorEnabled();

	boolean isInfoEnabled();

	boolean isTraceEnabled();

	boolean isWarnEnabled();

	void traceToken(String token,String format, Object... arguments);
	
	void traceToken(String token,String message, Throwable t);
	
	void trace(String format, Object... arguments);
	
	void trace(String message, Throwable t);
	
	
	void debugToken(String token,String format, Object... arguments);

	void debugToken(String token,String message, Throwable t);

	void debug(String format, Object... arguments);
	
	void debug(String message, Throwable t);
	
	
	void infoToken(String token,String format, Object... arguments);

	void infoToken(String token,String message, Throwable t);

	void info(String format, Object... arguments);

	void info(String message, Throwable t);


	void warnToken(String token,String format, Object... arguments);
	
	void warnToken(String token,String message, Throwable t);

	void warn(String format, Object... arguments);

	void warn(String message, Throwable t);

	
	void errorToken(String token,String format, Object... arguments);
	
	void errorToken(String token,String message, Throwable t);
	
	void error(String format, Object... arguments);
	
	void error(String message, Throwable t);
}