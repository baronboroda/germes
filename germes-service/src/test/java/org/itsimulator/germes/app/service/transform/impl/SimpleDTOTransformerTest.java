package org.itsimulator.germes.app.service.transform.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.itsimulator.germes.app.infra.exception.flow.InvalidParameterException;
import org.itsimulator.germes.app.model.entity.geography.City;
import org.itsimulator.germes.app.rest.dto.CityDTO;
import org.itsimulator.germes.app.service.transform.Transformer;
import org.junit.Before;
import org.junit.Test;

public class SimpleDTOTransformerTest {

	private Transformer transformer;
	
	@Before
	public void setup() {
		transformer = new SimpleDTOTransformer();
	}
	
	@Test
	public void testTransformCitySuccess() {
		City city = new City("Lviv");
		city.setId(1);
		city.setRegion("Lv");
		city.setDistrict("None");
		
		CityDTO dto = transformer.transform(city, CityDTO.class);
		assertNotNull(dto);
		assertEquals(dto.getId(), city.getId());
		assertEquals(dto.getName(), city.getName());
		assertEquals(dto.getDistinct(), city.getDistrict());
		assertEquals(dto.getRegion(), city.getRegion());
	}
	
	@Test(expected=InvalidParameterException.class)
	public void testTransformNullCityFailure() {
		transformer.transform(null, CityDTO.class);
	}
	
	@Test(expected=InvalidParameterException.class)
	public void testTransformNullDTOClassFailure() {
		transformer.transform(new City("Lviv"), null);
	}
	
	@Test
	public void testUnTransformCitySuccess() {
		CityDTO dto = new CityDTO();
		dto.setId(1);
		dto.setRegion("Lv");
		dto.setDistinct("None");
		dto.setName("Lviv");
		
		City city = transformer.untransform(dto, City.class);
		assertNotNull(city);
		assertEquals(dto.getId(), city.getId());
		assertEquals(dto.getName(), city.getName());
		assertEquals(dto.getRegion(), city.getRegion());
		assertEquals(dto.getDistinct(), city.getDistrict());
	}
	
	@Test(expected=InvalidParameterException.class)
	public void testUnTransformNullCityFailure() {
		transformer.untransform(null, City.class);
	}
	
	@Test(expected=InvalidParameterException.class)
	public void testUnTransformNullEntityClassFailure() {
		transformer.untransform(new CityDTO(), null);
	}
}
