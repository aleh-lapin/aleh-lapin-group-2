package com.epam.loaders;

import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.*;
import java.security.cert.Certificate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class MyClassLoader extends SecureClassLoader {
	private static final Logger LOG = Logger.getLogger(MyClassLoader.class);

	private ZipFile file;

	public MyClassLoader(String jarPath) throws IOException {
		super(MyClassLoader.class.getClassLoader());
		this.file = new ZipFile(jarPath);
	}

	protected Class<?> findClass(final String name) throws ClassNotFoundException {
		SecurityManager sm = System.getSecurityManager();
		// First check if we have permission to access the package.
		if (sm != null) {
			int i = name.lastIndexOf('.');
			if (i != -1) {
				sm.checkPackageDefinition(name.substring(0, i));
			}
		}
		// Now read in the bytes and define the class
		Class<?> result = findLoadedClass(name);
		if (result != null) {
			LOG.info("% Class " + name + " found in cache");
			return result;
		}
		
		final String className = convertName(name);
		final ZipEntry entry = file.getEntry(className);
		
		LOG.info("% Class " + name + entry == null ? "" : " found in " + entry);
		
		if (entry == null) {
			return findSystemClass(name);
		}
		
		try {
			return (Class<?>) AccessController
					.doPrivileged(new PrivilegedExceptionAction<Object>() {

						public Object run() throws Exception {
							byte[] classBytes = loadFileAsBytes(file.getInputStream(entry));
							CodeSource cs = getCodeSource(className);
							return defineClass(name, classBytes, 0,
									classBytes.length, cs);
						}
					});

		} catch (ClassFormatError e) {
			throw new ClassNotFoundException(
					"Format of class file incorrect for class " + name + ": "
							+ e);
		} catch (PrivilegedActionException e) {
			throw new ClassNotFoundException(
					"Format of class file incorrect for class " + name + ": "
							+ e);
		}
	}

	protected CodeSource getCodeSource(String name) {
		Certificate[] cert = null;
		return new CodeSource(findResource(name), cert);
	}

//	protected PermissionCollection getPermissions(CodeSource codesource) {
//		PermissionCollection pc = new Permissions();
//		pc.add(new RuntimePermission("createClassLoader"));
//		pc.add(new RuntimePermission("exitVM"));
//		return pc;
//	}

	protected URL findResource(String name) {
		ZipEntry entry = file.getEntry(name);
		if (entry == null) {
			return null;
		}
		try {
			return new URL("jar:file:" + file.getName() + "!/"
					+ entry.getName());
		} catch (MalformedURLException exception) {
			return null;
		}
	}
	
	private String convertName(String name) {
		return name.replace('.', '/') + ".class";
	}

	private static byte[] loadFileAsBytes(InputStream in) throws IOException {
		try {
			byte[] array = new byte[1024];
			ByteArrayOutputStream out = new ByteArrayOutputStream(array.length);
			int length = in.read(array);
			while (length > 0) {
				out.write(array, 0, length);
				length = in.read(array);
			}
			return out.toByteArray();
		} finally {
			try {
				in.close();
			} catch (Exception e) {
				LOG.error(e.getMessage());
			}
		}
	}
}
