package com.kikolski.aop;

import org.jboss.logging.Logger;

public class LoggingAspect {
	private final static Logger LOGGER = Logger.getLogger(LoggingAspect.class.getName());
	
	public void addLog(Object object) {
		LOGGER.info("Obiekt przekazany do serwisu [" +  object.getClass() + "]: " + object.toString());
		
	}
}
