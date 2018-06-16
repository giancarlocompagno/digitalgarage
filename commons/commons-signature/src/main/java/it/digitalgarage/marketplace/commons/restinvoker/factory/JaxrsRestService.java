package it.digitalgarage.marketplace.commons.restinvoker.factory;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// TODO: Auto-generated Javadoc
/**
 * The Interface JaxrsRestService.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JaxrsRestService{
  
  /**
   * Base uri.
   *
   * @return the string
   */
  String baseUri() default "";
  
}
