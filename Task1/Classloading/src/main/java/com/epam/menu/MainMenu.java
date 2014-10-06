package com.epam.menu;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.epam.loaders.MyClassLoader;
import com.epam.modules.ClassRepo;

public class MainMenu {
	private static final Logger LOG = Logger.getLogger(MainMenu.class);

	private final static String EXIT_CODE = "-1";

	private Scanner sc = new Scanner(System.in);

	private class SubMenu {

		private ClassRepo classRepo;

		public SubMenu(ClassRepo classRepo) {
			this.classRepo = classRepo;
		}

		private void showClasses() {
			int i = 0;
			for (String className : classRepo.getClassesNames()) {
				System.out.println(i++ + ":" + className);
			}
		}

		public String getClassName() {
			while (true) {
				showClasses();
				System.out.println("Enter class number or '-1' to exit:");
				String num = sc.nextLine();
				if (!EXIT_CODE.equals(num)) {
					try {
						return classRepo.getClassName(Integer.parseInt(num));
					} catch (NumberFormatException e) {
						LOG.error(e);
					} catch (IndexOutOfBoundsException e) {
						LOG.error(e);
					}
				} else {
					return EXIT_CODE;
				}
			}
		}
		
	}

	public void start() {
		while (true) {
			System.out.println("Enter path to jar file or '-1' to exit:");
			String path = sc.nextLine();
			if (!EXIT_CODE.equals(path)) {
				try {
					ClassRepo classRepo = new ClassRepo(path);
					SubMenu subMenu = new SubMenu(classRepo);
					String className = subMenu.getClassName();
					if (!EXIT_CODE.equals(className)) {
						ClassLoader loader = new MyClassLoader(path);
						Class<?> clazz = Class.forName(className, true, loader);
						Object object = clazz.newInstance();
						LOG.info(object.toString());
					}
				} catch (Exception e) {
					LOG.error(e);
				}
			} else {
				return;
			}
		}
	}

}
