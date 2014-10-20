package com.epam;

public class MyClassLoader extends ClassLoader {

	public Class<?> defineClass(String name, byte[] byteCode) {
		return super.defineClass(name, byteCode, 0, byteCode.length);
	}

}
