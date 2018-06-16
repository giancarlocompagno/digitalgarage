package it.digitalgarage.marketplace.commons.restinvoker.signature;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message){super(message);}
    public EntityNotFoundException(Exception e){super(e);}
}
