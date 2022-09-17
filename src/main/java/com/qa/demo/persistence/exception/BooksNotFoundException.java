package com.qa.demo.persistence.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "The person with that id does not exist")
public class BooksNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2304181817015989070L;

}

