package it.digitalgarage.marketplace.commons.restinvoker.signature;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus()
public class NotDeletedException extends RuntimeException {

    public NotDeletedException(String message){super(message);}
    public NotDeletedException(Exception e){super(e);}
}
