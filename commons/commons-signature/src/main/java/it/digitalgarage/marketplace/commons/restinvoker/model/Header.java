package it.digitalgarage.marketplace.commons.restinvoker.model;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// TODO: Auto-generated Javadoc
/**
 * Since spring 3.2.8 is missing an annotation for mapping HTTP headers to parameters, we're inventing our own.
 * @author George Georgovassilis
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Header {

    /**
     * The name of the HTTP header to bind the method parameter to.
     *
     * @return the string
     */
    String value() default "";

}
