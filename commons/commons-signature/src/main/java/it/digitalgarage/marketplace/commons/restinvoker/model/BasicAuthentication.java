package it.digitalgarage.marketplace.commons.restinvoker.model;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BasicAuthentication {
	
	 /**
     * The name of the HTTP header to bind the method parameter to.
     *
     * @return the string
     */
    String username();
    String password();

}
