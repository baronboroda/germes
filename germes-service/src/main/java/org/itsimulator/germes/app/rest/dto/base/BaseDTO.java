package org.itsimulator.germes.app.rest.dto.base;

import org.itsimulator.germes.app.model.entity.base.AbstractEntity;

public abstract class BaseDTO<T extends AbstractEntity> {

	private int id;
	
	public void transform(T t) {
		id = t.getId();
	}
	
	public T unstansform(T t) {
		t.setId(getId());
		return t;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}
