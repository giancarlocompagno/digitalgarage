package it.digitalgarage.marketplace.offertaasta.be;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Main {

	
	public static void main(String[] args) throws ClassNotFoundException {
		Class clazz = Class.forName("it.digitalgarage.marketplace.offertaasta.be.rest.UtenteDTO");
		
		System.out.println(clazz.getName());
		for(Field f :clazz.getDeclaredFields()){
			System.out.println(f.getName()+" "+f.getType());
		}
		
		for(Method m : clazz.getDeclaredMethods()){
			System.out.println(m.getReturnType()+" "+m.getName()+"("+Arrays.toString(m.getParameters())+")");
		}
		
	}
	
}
