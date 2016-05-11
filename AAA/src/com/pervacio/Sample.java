package com.pervacio;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Sample {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
//		Constructor<Example> constructor = Example.class.getConstructor();
//		constructor.setAccessible(true);
//		constructor.newInstance();
		
		Constructor<Example> constructor;
        constructor = Example.class.getDeclaredConstructor();
        
        Method method=null;
        
        constructor.setAccessible(true);
        Example  foo = constructor.newInstance();
        System.out.println(foo);

	}

}
