package it.digitalgarage.marketplace.commons.be.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidationInfoProperty {
     String repository() default "";  /* nome specifico del repository */
     boolean ignore()   default false;/* ignore del campo selezionato */
     String fieldValue() default ""; /* fieldValue specifico */
}
