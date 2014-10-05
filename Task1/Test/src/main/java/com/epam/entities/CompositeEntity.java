package com.epam.entities;

public class CompositeEntity {

	private SimpleEntity simpleEntity = new SimpleEntity();

	@Override
	public String toString() {
		return "This is a composite entity: " + simpleEntity.toString();
	}
	
}
