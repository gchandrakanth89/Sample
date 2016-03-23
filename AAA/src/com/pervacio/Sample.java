package com.pervacio;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Sample {

	public static void main(String[] args) {
		MyObject myObject = new MyObject();
		Class<? extends MyObject> class1 = myObject.getClass();
		try {
			Method declaredMethod = class1.getDeclaredMethod("getL");
			declaredMethod.setAccessible(true);
			Long invoke = (Long) declaredMethod.invoke(myObject);
			System.out.println(invoke);

			Field declaredField = class1.getDeclaredField("str");
			declaredField.setAccessible(true);
			Object object = declaredField.get(myObject);
			System.out.println(object.toString());

		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

}
