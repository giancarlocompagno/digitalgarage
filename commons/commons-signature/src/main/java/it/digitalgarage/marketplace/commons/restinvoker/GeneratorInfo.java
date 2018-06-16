package it.digitalgarage.marketplace.commons.restinvoker;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GeneratorInfo {
	
	boolean generate() default true;
	boolean required() default false;
	boolean disabled() default false;
	boolean editabled() default true;
	String type() default "text";
	String defaultValue() default "";
	

}
