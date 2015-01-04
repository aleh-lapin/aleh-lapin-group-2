package by.pochem.web.jsp.tags;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public final class ClassUtils {
	
	public static Class<?> loadClass(String className)
			throws ClassNotFoundException {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();

		if (classLoader == null) {
			classLoader = ClassUtils.class.getClassLoader();
		}

		return classLoader.loadClass(className);
	}

	public static Map<String, Object> getClassConstants(String className)
			throws ClassNotFoundException, IllegalArgumentException,
			IllegalAccessException {
		Map<String, Object> constants = new HashMap<String, Object>();
		Class<?> clazz = loadClass(className);
		getClassConstants(clazz.getSimpleName(), clazz, constants);
		return constants;
	}
	
	private static void getClassConstants(String classPath, Class<?> clazz, Map<String, Object> constants) throws IllegalArgumentException, IllegalAccessException {
		Class<?>[] clazzes = clazz.getClasses();
		for (Class<?> clazzz : clazzes) {
			getClassConstants(classPath + "." + clazzz.getSimpleName(), clazzz, constants);
		}
		Field[] fields = clazz.getFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			int modifiers = field.getModifiers();
			if (((modifiers & 0x8) != 0) && ((modifiers & 0x10) != 0)) {
				Object value = field.get(null);
				if (value != null) {
					constants.put(classPath + "." + field.getName(), value);
				}
			}
		}
		
	}
	
}
