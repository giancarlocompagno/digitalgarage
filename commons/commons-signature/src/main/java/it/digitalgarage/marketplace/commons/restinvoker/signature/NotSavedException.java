package it.digitalgarage.marketplace.commons.restinvoker.signature;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_MODIFIED)
public class NotSavedException extends RuntimeException {

    public NotSavedException(String message){super(message);}
    public NotSavedException(Exception e){super(e);}
}
