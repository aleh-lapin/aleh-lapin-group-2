package by.pochem.web.handlers;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {
	private static final Logger LOG = Logger.getLogger(ControllerExceptionHandler.class);
	
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoHandlerFoundException.class)
	public void notFound() {
		LOG.error("ALARAME!");
	}
	
}
