package it.digitalgarage.marketplace.commons.restinvoker.factory.util;



import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;


// TODO: Auto-generated Javadoc
/**
 * The Class TypeUtils.
 */
public class TypeUtils {
  
  /** The Constant RESOURCE_PATTERN. */
  private static final String RESOURCE_PATTERN = "/**/*.class";
  
  /** The Constant resourcePatternResolver. */
  private final static ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
  
  /** The Constant readerFactory. */
  private final static MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
  
  /**
   * Instantiates a new type utils.
   */
  private TypeUtils(){}
  
  /**
   * Find sub types.
   *
   * @param <T> the generic type
   * @param packageName the package name
   * @param superType the super type
   * @return the list
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ClassNotFoundException the class not found exception
   */
  public static <T> List<Class<T>> findSubTypes(String packageName, Class<T> superType) throws IOException, ClassNotFoundException {
    List<Class<T>> types=findTypes(packageName, superType);
    types.remove(superType);
    return types;
  }  

  /**
   * Find types.
   *
   * @param <T> the generic type
   * @param packageName the package name
   * @param type the type
   * @return the list
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ClassNotFoundException the class not found exception
   */
  public static <T> List<Class<T>> findTypes(String packageName, Class<T> type) throws IOException, ClassNotFoundException {
    AssignableTypeFilter filter = new AssignableTypeFilter(type);
    return findClasses(type,packageName, filter);
  }    
  
  /**
   * Find classes.
   *
   * @param <T> the generic type
   * @param clazz the clazz
   * @param packageName the package name
   * @param filter the filter
   * @return the list
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ClassNotFoundException the class not found exception
   */
  @SuppressWarnings("unchecked")
  public static <T> List<Class<T>> findClasses(Class<T> clazz, String packageName, TypeFilter filter) throws IOException, ClassNotFoundException {
    List<Class<T>> res = new ArrayList<Class<T>>();
    String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(packageName) + RESOURCE_PATTERN;
    Resource[] resources = resourcePatternResolver.getResources(pattern);
    for (Resource resource : resources) {
      if (resource.isReadable()) {
        MetadataReader reader = readerFactory.getMetadataReader(resource);
        String className = reader.getClassMetadata().getClassName();
        if (filter.match(reader, readerFactory)) {
          res.add((Class<T>)resourcePatternResolver.getClassLoader().loadClass(className));
        }
      }
    }

    return res;
  }

  /**
   * Gets the parameterized type.
   *
   * @param t the t
   * @return the parameterized type
   */
  private static Type getParameterizedType(Type t){
    if (t instanceof  ParameterizedType){
      ParameterizedType pt = (ParameterizedType) t;
      Type res=pt.getActualTypeArguments()[0];
      if (!res.getClass().getName().endsWith(".TypeVariableImpl")) {
        return res;  
      };
    }
    return null;
  }
  
  /**
   * Gets the parameterized type.
   *
   * @param <T> the generic type
   * @param parametrizedClass the parametrized class
   * @return the parameterized type
   */
  private static <T> Type getParameterizedType(Class<T> parametrizedClass){
    Type t = parametrizedClass.getGenericSuperclass();
    Type res= getParameterizedType(t);
    if (res!= null) return res;
    for (Type tt:parametrizedClass.getGenericInterfaces()){
      res= getParameterizedType(tt);
      if (res!= null) return res;
    }
    return res;
  }
  
  /**
   * Extract actual type.
   *
   * @param <T> the generic type
   * @param parametrizedClass the parametrized class
   * @return the type
   */
  public static <T> Type extractActualType(Class<T> parametrizedClass){
    Type t = getParameterizedType(parametrizedClass);
    if (t!= null) return t;
    t = getParameterizedType(parametrizedClass.getSuperclass());
    return t;
  }
  

  /**
   * Se obj implementa/estende T ritorna T altrimenti ritorna null.
   * E' equivalente ad instanceof e relativo cast
   *
   * @param <T> the generic type
   * @param obj the obj
   * @param clazz the clazz
   * @return the implemented type
   */
  @SuppressWarnings("unchecked")
  public static  <T> T getImplementedType(Object obj,Class<T>clazz) {
    if (clazz.isAssignableFrom(obj.getClass())){
      return (T)obj;
    }
    return null;
  }
  
 
}
