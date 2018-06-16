package it.digitalgarage.marketplace.commons.logging;

class LoggerImpl implements Logger{

	public static final String SUBSYSTEM_ID_ = "SUBSYSTEM_ID";
	public static final String TOKEN_ = "TOKEN";

	private org.slf4j.Logger logger;
	
	private String subSystemID;
	
	public LoggerImpl(org.slf4j.Logger logger,String subSystemID) {
		this.logger = logger;
		this.subSystemID = subSystemID;
	}
	
	private void set(String token) {
		if(subSystemID!=null){
			org.slf4j.MDC.put(SUBSYSTEM_ID_,subSystemID);
		}else{
			org.slf4j.MDC.put(SUBSYSTEM_ID_,"platform");
		}
		if(token!=null){
			org.slf4j.MDC.put(TOKEN_, token);
		}else{
			org.slf4j.MDC.put(TOKEN_, ""+Thread.currentThread().getId());
		}
	}

	private void clear() {
		org.slf4j.MDC.remove(TOKEN_);
		org.slf4j.MDC.remove(SUBSYSTEM_ID_);
	}
	
	/* (non-Javadoc)
	 * @see it.eng.giustizia.middleware.trace.Loggerz#getName()
	 */
	@Override
	public String getName() {
		return logger.getName();
	}
	
	/* (non-Javadoc)
	 * @see it.eng.giustizia.middleware.trace.Loggerz#isDebugEnabled()
	 */
	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	/* (non-Javadoc)
	 * @see it.eng.giustizia.middleware.trace.Loggerz#isErrorEnabled()
	 */
	@Override
	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}

	/* (non-Javadoc)
	 * @see it.eng.giustizia.middleware.trace.Loggerz#isInfoEnabled()
	 */
	@Override
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	/* (non-Javadoc)
	 * @see it.eng.giustizia.middleware.trace.Loggerz#isTraceEnabled()
	 */
	@Override
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	/* (non-Javadoc)
	 * @see it.eng.giustizia.middleware.trace.Loggerz#isWarnEnabled()
	 */
	@Override
	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}


	/* (non-Javadoc)
	 * @see it.eng.giustizia.middleware.trace.Loggerz#debug(it.eng.giustizia.middleware.trace.MDC.SYSTEM, java.lang.String, java.lang.String, java.lang.Object)
	 */
	@Override
	public void debugToken(String token,String format, Object... arguments) {
		set(token);
		logger.debug(format, arguments);
		clear();
	}

	/* (non-Javadoc)
	 * @see it.eng.giustizia.middleware.trace.Loggerz#debug(it.eng.giustizia.middleware.trace.MDC.SYSTEM, java.lang.String, java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void debugToken(String token,String message, Throwable t) {
		set(token);
		logger.debug(message, t);
		clear();
	}

	/* (non-Javadoc)
	 * @see it.eng.giustizia.middleware.trace.Loggerz#error(it.eng.giustizia.middleware.trace.MDC.SYSTEM, java.lang.String, java.lang.String, java.lang.Object)
	 */
	@Override
	public void errorToken(String token,String format, Object... arguments) {
		set(token);
		logger.error(format, arguments);
		clear();
	}


	/* (non-Javadoc)
	 * @see it.eng.giustizia.middleware.trace.Loggerz#error(it.eng.giustizia.middleware.trace.MDC.SYSTEM, java.lang.String, java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void errorToken(String token,String message, Throwable t) {
		set(token);
		logger.error(message, t);
	}


	/* (non-Javadoc)
	 * @see it.eng.giustizia.middleware.trace.Loggerz#info(it.eng.giustizia.middleware.trace.MDC.SYSTEM, java.lang.String, java.lang.String, java.lang.Object)
	 */
	@Override
	public void infoToken(String token,String format, Object... arguments) {
		set(token);
		logger.info(format, arguments);
		clear();
	}

	/* (non-Javadoc)
	 * @see it.eng.giustizia.middleware.trace.Loggerz#info(it.eng.giustizia.middleware.trace.MDC.SYSTEM, java.lang.String, java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void infoToken(String token,String message, Throwable t) {
		set(token);
		logger.info(message, t);
		clear();
	}

	/* (non-Javadoc)
	 * @see it.eng.giustizia.middleware.trace.Loggerz#trace(it.eng.giustizia.middleware.trace.MDC.SYSTEM, java.lang.String, java.lang.String, java.lang.Object)
	 */
	@Override
	public void traceToken(String token,String format, Object... arguments) {
		set(token);
		logger.trace(format, arguments);
		clear();
	}

	/* (non-Javadoc)
	 * @see it.eng.giustizia.middleware.trace.Loggerz#trace(it.eng.giustizia.middleware.trace.MDC.SYSTEM, java.lang.String, java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void traceToken(String token,String message, Throwable t) {
		set(token);
		logger.trace(message, t);
		clear();
	}

	/* (non-Javadoc)
	 * @see it.eng.giustizia.middleware.trace.Loggerz#warn(it.eng.giustizia.middleware.trace.MDC.SYSTEM, java.lang.String, java.lang.String, java.lang.Object)
	 */
	@Override
	public void warnToken(String token,String format, Object... arguments) {
		set(token);
		logger.warn(format, arguments);
		clear();
	}

	/* (non-Javadoc)
	 * @see it.eng.giustizia.middleware.trace.Loggerz#warn(it.eng.giustizia.middleware.trace.MDC.SYSTEM, java.lang.String, java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void warnToken(String token,String message, Throwable t) {
		set(token);
		logger.warn(message, t);
		clear();
	}

	/* (non-Javadoc)
	 * @see it.eng.giustizia.middleware.trace.Loggerz#debug(it.eng.giustizia.middleware.trace.MDC.SYSTEM, java.lang.String, java.lang.Object)
	 */
	@Override
	public void debug(String format, Object... arguments) {
		set(null);
		logger.debug(format, arguments);
		clear();
	}

	/* (non-Javadoc)
	 * @see it.eng.giustizia.middleware.trace.Loggerz#debug(it.eng.giustizia.middleware.trace.MDC.SYSTEM, java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void debug(String message, Throwable t) {
		set(null);
		logger.debug(message, t);
		clear();
	}

	/* (non-Javadoc)
	 * @see it.eng.giustizia.middleware.trace.Loggerz#error(it.eng.giustizia.middleware.trace.MDC.SYSTEM, java.lang.String, java.lang.Object)
	 */
	@Override
	public void error(String format, Object... arguments) {
		set(null);
		logger.error(format, arguments);
		clear();
	}


	/* (non-Javadoc)
	 * @see it.eng.giustizia.middleware.trace.Loggerz#error(it.eng.giustizia.middleware.trace.MDC.SYSTEM, java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void error(String message, Throwable t) {
		set(null);
		logger.error(message, t);
	}


	/* (non-Javadoc)
	 * @see it.eng.giustizia.middleware.trace.Loggerz#info(it.eng.giustizia.middleware.trace.MDC.SYSTEM, java.lang.String, java.lang.Object)
	 */
	@Override
	public void info(String format, Object... arguments) {
		set(null);
		logger.info(format, arguments);
		clear();
	}

	/* (non-Javadoc)
	 * @see it.eng.giustizia.middleware.trace.Loggerz#info(it.eng.giustizia.middleware.trace.MDC.SYSTEM, java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void info(String message, Throwable t) {
		set(null);
		logger.info(message, t);
		clear();
	}

	/* (non-Javadoc)
	 * @see it.eng.giustizia.middleware.trace.Loggerz#trace(it.eng.giustizia.middleware.trace.MDC.SYSTEM, java.lang.String, java.lang.Object)
	 */
	@Override
	public void trace(String format, Object... arguments) {
		set(null);
		logger.trace(format, arguments);
		clear();
	}

	/* (non-Javadoc)
	 * @see it.eng.giustizia.middleware.trace.Loggerz#trace(it.eng.giustizia.middleware.trace.MDC.SYSTEM, java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void trace(String message, Throwable t) {
		set(null);
		logger.trace(message, t);
		clear();
	}

	/* (non-Javadoc)
	 * @see it.eng.giustizia.middleware.trace.Loggerz#warn(it.eng.giustizia.middleware.trace.MDC.SYSTEM, java.lang.String, java.lang.Object)
	 */
	@Override
	public void warn(String format, Object... arguments) {
		set(null);
		logger.warn(format, arguments);
		clear();
	}

	/* (non-Javadoc)
	 * @see it.eng.giustizia.middleware.trace.Loggerz#warn(it.eng.giustizia.middleware.trace.MDC.SYSTEM, java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void warn(String message, Throwable t) {
		set(null);
		logger.warn(message, t);
		clear();
	}

}
