package it.digitalgarage.marketplace.commons.restinvoker.factory.util;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;

import org.springframework.core.type.filter.AnnotationTypeFilter;

// TODO: Auto-generated Javadoc
/**
 * The Class AnnotationUtils.
 */
public class AnnotationUtils {
  
  /**
   * Instantiates a new annotation utils.
   */
  private AnnotationUtils(){}
  
  /**
   * Find annotated classes.
   *
   * @param packageName the package name
   * @param annotation the annotation
   * @return the list
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ClassNotFoundException the class not found exception
   */
  public static List<Class<Object>> findAnnotatedClasses(String packageName, Class<? extends Annotation> annotation) throws IOException, ClassNotFoundException {
    AnnotationTypeFilter filter = new AnnotationTypeFilter(annotation);
    return TypeUtils.findClasses(Object.class, packageName, filter);
  }


}