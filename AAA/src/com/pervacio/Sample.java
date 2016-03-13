package com.pervacio;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Sample {

	public static void main(String[] args) {
		Class<MyObject> myClass = MyObject.class;
		try {
			// Get the field
			Field declaredField = myClass.getDeclaredField("str");
			String fieldName = declaredField.getName();
			System.out.println("Field name = " + fieldName);
			Class<?> fieldType = declaredField.getType();
			System.out.println("Type = " + fieldType.getSimpleName());

			// Get field value
			MyObject myObject = new MyObject();
			declaredField.set(myObject, "Chandrakanth");
			System.out.println(myObject.getStr());

			// Get methods
			Method declaredMethod = myClass.getDeclaredMethod("getI", null);
			// If the method is private then setAccessible to true
			declaredMethod.setAccessible(true);
			Object invoke = declaredMethod.invoke(myObject, null);
			System.out.println(invoke.toString());

			System.out.println("Hiiiiii");
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
