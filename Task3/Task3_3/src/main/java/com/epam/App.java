package com.epam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class App {
	private static final Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) throws IOException {
		List<byte[]> v = new ArrayList<byte[]>();
		try {
			while (true) {
				v.add(new byte[1024 * 1024]);
			}
		} catch (Error e) {
			logger.error("Heap OOM: ", e);
			v.clear();

			List<ClassLoader> loaders = new ArrayList<ClassLoader>();
			
			Class<?> clazz = App.class;
			byte[] buffer = ClassLoaderUtil
					.loadByteCode(clazz, clazz.getName());

			MyClassLoader loader = new MyClassLoader();
			try {
				while (true) {
					loader.defineClass(clazz.getName(), buffer);
					loaders.add(loader);
					loader = new MyClassLoader();
				}
			} catch (Error e2) {
				logger.error("Perm OOM: " + e2);
			}
		}
	}

}
