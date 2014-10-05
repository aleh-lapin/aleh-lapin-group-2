package com.epam.modules;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassRepo {

	private JarFile jarFile;

	private List<String> classesNames = new ArrayList<String>();

	public ClassRepo(String path) throws IOException {
		File file = new File(path);
		if (file.exists()) {
			jarFile = new JarFile(file);
			init();
		} else {
			throw new IOException("File doesn't exist");
		}
	}

	public void init() {
		Enumeration<JarEntry> e = jarFile.entries();
		while (e.hasMoreElements()) {
			JarEntry je = (JarEntry) e.nextElement();
			if (je.isDirectory() || !je.getName().endsWith(".class")) {
				continue;
			}
			String className = je.getName()
					.substring(0, je.getName().length() - 6).replace('/', '.');
			classesNames.add(className);
		}
	}
	
	public List<String> getClassesNames() {
		return classesNames;
	}
	
	public String getClassName(int i) {
		return classesNames.get(i);
	}
} 
