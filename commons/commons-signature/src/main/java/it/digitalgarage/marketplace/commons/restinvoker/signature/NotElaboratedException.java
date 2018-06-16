package it.digitalgarage.marketplace.commons.restinvoker.signature;


import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class NotElaboratedException extends RuntimeException {

    public NotElaboratedException(String message){super(message);}
    public NotElaboratedException(Exception e){super(e);}
    public NotElaboratedException(String message,Exception e){super(message,e);}


}
