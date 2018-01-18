package org.itsimulator.germes.app.rest.dto;

import org.itsimulator.germes.app.model.entity.geography.City;
import org.itsimulator.germes.app.rest.dto.base.BaseDTO;

public class CityDTO extends BaseDTO<City> {

	private String name;
	
	private String distinct;
	
	private String region;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDistinct() {
		return distinct;
	}
	
	public void setDistinct(String distinct) {
		this.distinct = distinct;
	}
	
	public String getRegion() {
		return region;
	}
	
	public void setRegion(String region) {
		this.region = region;
	}
}
