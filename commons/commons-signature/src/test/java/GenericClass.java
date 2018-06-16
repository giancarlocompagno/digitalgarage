//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//import java.lang.reflect.TypeVariable;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//
//import com.google.common.reflect.TypeToken;
//
//
//public class GenericClass<T> {
//	
//	public static <T> Type[] resolveActualTypeArgs (Class<? extends T> offspring, Class<T> base, Type... actualArgs) {
//
//	    assert offspring != null;
//	    assert base != null;
//	    assert actualArgs.length == 0 || actualArgs.length == offspring.getTypeParameters().length;
//
//	    //  If actual types are omitted, the type parameters will be used instead.
//	    if (actualArgs.length == 0) {
//	        actualArgs = offspring.getTypeParameters();
//	    }
//	    // map type parameters into the actual types
//	    Map<String, Type> typeVariables = new HashMap<String, Type>();
//	    for (int i = 0; i < actualArgs.length; i++) {
//	        TypeVariable<?> typeVariable = (TypeVariable<?>) offspring.getTypeParameters()[i];
//	        typeVariables.put(typeVariable.getName(), actualArgs[i]);
//	    }
//
//	    // Find direct ancestors (superclass, interfaces)
//	    List<Type> ancestors = new LinkedList<Type>();
//	    if (offspring.getGenericSuperclass() != null) {
//	        ancestors.add(offspring.getGenericSuperclass());
//	    }
//	    for (Type t : offspring.getGenericInterfaces()) {
//	        ancestors.add(t);
//	    }
//
//	    // Recurse into ancestors (superclass, interfaces)
//	    for (Type type : ancestors) {
//	        if (type instanceof Class<?>) {
//	            // ancestor is non-parameterized. Recurse only if it matches the base class.
//	            Class<?> ancestorClass = (Class<?>) type;
//	            if (base.isAssignableFrom(ancestorClass)) {
//	                Type[] result = resolveActualTypeArgs((Class<? extends T>) ancestorClass, base);
//	                if (result != null) {
//	                    return result;
//	                }
//	            }
//	        }
//	        if (type instanceof ParameterizedType) {
//	            // ancestor is parameterized. Recurse only if the raw type matches the base class.
//	            ParameterizedType parameterizedType = (ParameterizedType) type;
//	            Type rawType = parameterizedType.getRawType();
//	            if (rawType instanceof Class<?>) {
//	                Class<?> rawTypeClass = (Class<?>) rawType;
//	                if (base.isAssignableFrom(rawTypeClass)) {
//
//	                    // loop through all type arguments and replace type variables with the actually known types
//	                    List<Type> resolvedTypes = new LinkedList<Type>();
//	                    for (Type t : parameterizedType.getActualTypeArguments()) {
//	                        if (t instanceof TypeVariable<?>) {
//	                            Type resolvedType = typeVariables.get(((TypeVariable<?>) t).getName());
//	                            resolvedTypes.add(resolvedType != null ? resolvedType : t);
//	                        } else {
//	                            resolvedTypes.add(t);
//	                        }
//	                    }
//
//	                    Type[] result = resolveActualTypeArgs((Class<? extends T>) rawTypeClass, base, resolvedTypes.toArray(new Type[] {}));
//	                    if (result != null) {
//	                        return result;
//	                    }
//	                }
//	            }
//	        }
//	    }
//
//	    // we have a result if we reached the base class.
//	    return offspring.equals(base) ? actualArgs : null;
//	}
//	
//	  private final TypeToken<T> typeToken = new TypeToken<T>(getClass()) { };
//	  private final Type type = typeToken.getType(); // or getRawType() to return Class<? super T>
//
//	  public Type getType() {
//	    return type;
//	  }
//
//	  public static void main(String[] args) {
//		  
//		List<String> l = new ArrayList<String>();
//		System.out.println(((ParameterizedType)l.getClass().getGenericInterfaces()[0]).getRawType());
//	    //GenericClass<String> example = new GenericClass<String>() { };
//	    //System.out.println(example.getType()); // => class java.lang.String
//	  }
//	}
