package com.hothome.exception.user;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hothome.controller.PropertyListingController;
import com.hothome.exception.property.PropertyNotFoundException;
import com.hothome.model.HttpResponse;

@ControllerAdvice(assignableTypes = PropertyListingController.class)
public class PropertyExceptionController implements ErrorController{

	public static final String ERROR_PATH = "/error";
	
	@ExceptionHandler(PropertyNotFoundException.class)
	public ResponseEntity<HttpResponse> propertyNotFoundException(PropertyNotFoundException exception){
		return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
	}
    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(), message), httpStatus);
    }
    @RequestMapping(ERROR_PATH)
    public ResponseEntity<HttpResponse> notFound404() {
        return createHttpResponse(NOT_FOUND, "There is no mapping for this URL");
    }
    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
