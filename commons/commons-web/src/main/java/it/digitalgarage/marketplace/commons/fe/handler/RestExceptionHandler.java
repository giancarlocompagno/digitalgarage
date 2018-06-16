package it.digitalgarage.marketplace.commons.fe.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import it.digitalgarage.marketplace.commons.be.validator.NotValidException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,WebRequest request) {
		return new ResponseEntity<Object>(new ApiErrors(ex),HttpStatus.BAD_REQUEST);
	}
	
	@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<Object>(new ApiErrors(ex.getBindingResult()),HttpStatus.BAD_REQUEST);
}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable mostSpecificCause = ex.getMostSpecificCause();
		if (mostSpecificCause != null) {
			if(mostSpecificCause instanceof JsonParseException){
				String message = ((JsonParseException)mostSpecificCause).getMessage();
				List<FieldError> f = new ArrayList<>();
				List<ObjectError> g = new ArrayList<>();
				g.add(new ObjectError("", message));
				return new ResponseEntity<Object>(new ApiErrors(g,f),HttpStatus.BAD_REQUEST);
			}else if(mostSpecificCause instanceof InvalidFormatException){
				List<Reference> paths = ((InvalidFormatException)mostSpecificCause).getPath();
				String field = "";
				String message = msg(((InvalidFormatException)mostSpecificCause).getTargetType(),((InvalidFormatException)mostSpecificCause).getOriginalMessage());
				Object value = ((InvalidFormatException)mostSpecificCause).getValue();
				for(Reference r :paths){
					if(r.getFieldName()!=null){
						field = field + (!field.isEmpty()?".":"")+r.getFieldName();
					}else if(r.getIndex()>=0){
						field = field + "["+r.getIndex()+"]";
					}
				}
				List<ObjectError> g = new ArrayList<>();
				List<FieldError> f = new ArrayList<>();
				f.add(new FieldError("", field, value,false,null,null, message));
				return new ResponseEntity<Object>(new ApiErrors(g,f),HttpStatus.BAD_REQUEST);
			}

		}
		return super.handleHttpMessageNotReadable(ex, headers, status, request);
	}

	@ExceptionHandler(NotValidException.class)
	public ResponseEntity<ApiErrors> notValidException(NotValidException e) {
		return new ResponseEntity<ApiErrors>(new ApiErrors(e.getErrors()),HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpStatusCodeException.class)
	public ResponseEntity<String> notValidException(HttpStatusCodeException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(e.getResponseBodyAsString());
	}
	
	/*@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ApiErrors> entityNotFoundException(EntityNotFoundException e){
		return new ResponseEntity<ApiErrors>(new ApiErrors(),HttpStatus.NOT_FOUND);
	}*/
	
	
	public String msg(Class clazz,String message){
		if(Integer.class.isAssignableFrom(clazz)){
			return "Il valore inserito deve essere un numero intero";
		}else if(Date.class.isAssignableFrom(clazz)){
			return "Il valore inserito non e' una data valida";
		}else{
			return message;
		}
 	}
	
	
	public static class ApiErrors{
		
		List<ObjectError> globals;
		List<FieldError> fields;
		
		public ApiErrors(){
			
		}
		
		public ApiErrors(Errors errors) {
			this.globals = errors.getGlobalErrors();
			this.fields = errors.getFieldErrors();
		}
		
		public ApiErrors(List<ObjectError> globals,List<FieldError> fields) {
			this.globals = globals;
			this.fields = fields;
		}
		
		
		public List<ObjectError> getGlobals() {
			return globals;
		}
		
		public List<FieldError> getFields() {
			return fields;
		}
		
	}
	
	
	public static class GlobalError{
		String message;
	}
	
}