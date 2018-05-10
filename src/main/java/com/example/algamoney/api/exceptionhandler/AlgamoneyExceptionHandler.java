package com.example.algamoney.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messages;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
	
		String message = messages.getMessage("user.invalid.message", null, LocaleContextHolder.getLocale());
		
		return super.handleExceptionInternal(ex, new Erro(message, ex), headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<Erro> erros =  this.getErros(ex.getBindingResult());
		return super.handleExceptionInternal(ex, erros, headers, status, request);
	}
	
	
	@ExceptionHandler({EmptyResultDataAccessException.class})
	public ResponseEntity<Object> handleEmptyResultDataException(EmptyResultDataAccessException ex, WebRequest request)
	{
		return super.handleExceptionInternal(ex, Arrays.asList(new Erro("NO MESSSAGE", ex)), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler({IllegalArgumentException.class})
	public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request)
	{
		return super.handleExceptionInternal(ex, Arrays.asList(new Erro("ILLEGAL VALUE",ex)), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	private List<Erro> getErros(BindingResult bindingResult) {
		List<FieldError> allErrors = bindingResult.getFieldErrors();
		List<Erro> created = new ArrayList<>();
		for(FieldError o : allErrors)
		{
			created.add(new Erro(this.messages.getMessage(o, LocaleContextHolder.getLocale()), new Exception(o.toString())));
		}
		
		return created;
	}


	public static class Erro
	{
		private String userMessage;
		
		private String exception; 
		
		
		public Erro(String userMessage, Exception exception)
		{
			this.userMessage = userMessage; 
			this.exception = exception.getMessage()	;
		}


		public String getUserMessage() {
			return userMessage;
		}


		public String getException() {
			return exception;
		}
		
	}
}
