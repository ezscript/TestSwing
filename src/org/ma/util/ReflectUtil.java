package org.ma.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;

public class ReflectUtil {
	
	public static URL getResource(String name) {
		URL url = null;
		ClassLoader classLoader = getCustomClassLoader();
		if (classLoader != null) {
			url = classLoader.getResource(name);
		}
		if (url == null) {
			// Try the current Thread context classloader
			classLoader = Thread.currentThread().getContextClassLoader();
			url = classLoader.getResource(name);
			if (url == null) {
				// Finally, try the classloader for this class
				classLoader = ReflectUtil.class.getClassLoader();
				url = classLoader.getResource(name);
			}
		}
		try {
			url = new URL(url.toURI().toASCIIString());
		} catch (Exception e) {
			throw new RuntimeException("error url:" + url, e);
		}
		return url;
	}

	public static InputStream getResourceAsStream(String name) {
		InputStream resourceStream = null;
		ClassLoader classLoader = getCustomClassLoader();
		if (classLoader != null) {
			resourceStream = classLoader.getResourceAsStream(name);
		}
		if (resourceStream == null) {
			// Try the current Thread context classloader
			classLoader = Thread.currentThread().getContextClassLoader();
			resourceStream = classLoader.getResourceAsStream(name);
			if (resourceStream == null) {
				// Finally, try the classloader for this class
				classLoader = ReflectUtil.class.getClassLoader();
				resourceStream = classLoader.getResourceAsStream(name);
			}
		}
		return resourceStream;
	}



	/**
	 * findMethod
	 * @param clazz
	 * @param methodName
	 * @param args
	 * @return
	 */
	public static Method findMethod(Class<? extends Object> clazz,
			String methodName, Object[] args) {
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.getName().equals(methodName)
					&& matches(method.getParameterTypes(), args)) {
				return method;
			}
		}
		Class<?> superClass = clazz.getSuperclass();
		if (superClass != null) {
			return findMethod(superClass, methodName, args);
		}
		return null;
	}

	public static boolean matches(Class<?>[] parameterTypes, Object[] args) {
		if ((parameterTypes == null) || (parameterTypes.length == 0)) {
			return ((args == null) || (args.length == 0));
		}
		if ((args == null) || (parameterTypes.length != args.length)) {
			return false;
		}
		for (int i = 0; i < parameterTypes.length; i++) {
			if ((args[i] != null)
					&& (!parameterTypes[i].isAssignableFrom(args[i].getClass()))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns the field of the given class or null if it doesnt exist.
	 */
	public static Field getField(String fieldName, Class<?> clazz) {
		Field field = null;
		try {
			field = clazz.getDeclaredField(fieldName);
		} catch (SecurityException e) {
			throw new RuntimeException("not allowed to access field " + field
					+ " on class " + clazz.getCanonicalName());
		} catch (NoSuchFieldException e) {
			// for some reason getDeclaredFields doesnt search superclasses
			// (which getFields() does ... but that gives only public fields)
			Class<?> superClass = clazz.getSuperclass();
			if (superClass != null) {
				return getField(fieldName, superClass);
			}
		}
		return field;
	}

	public static void setField(Field field, Object object, Object value) {
		try {
			field.setAccessible(true);
			field.set(object, value);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Could not set field "
					+ field.toString(), e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Could not set field "
					+ field.toString(), e);
		}
	}

	public static ClassLoader getCustomClassLoader() {
		// TODO customerContext.getClassLoader()

		return null;
	}

	public static BufferedReader getBufferReader(File file) throws IOException {
		return new BufferedReader(new FileReader(file));
	}

}
