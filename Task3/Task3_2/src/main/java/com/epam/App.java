package com.epam;

import org.apache.log4j.Logger;

public class App {
	private static final Logger logger = Logger.getLogger(App.class);
	
	
	private static class Entity {
		
		private String entityName;
		
		public Entity(String entityName) {
			this.entityName = entityName;
		}

		@Override
		public String toString() {
			return "Entity [entityName=" + entityName + "]";
		}
		
	}
	
	public static void main(String[] args) {

		int a = 10;
		scalarPassing(a);
		logger.info("Scalar passing without returning: " + a);
		a = scalarPassing(a);
		logger.info("Scalar passing with returning: " + a);
		
		Entity entity = new Entity("Initial entity");
		objectPassing(entity);
		logger.info("Object passing without returning: " + entity);
		entity = objectPassing(entity);
		logger.info("Object passing with returning: " + entity);
		
	}
	
	private static int scalarPassing(int a) {
		a = a * 2;
		return a;
	}
	
	private static Entity objectPassing(Entity entity) {
		entity = new Entity("Inside passing method");
		return entity;
	}

}
