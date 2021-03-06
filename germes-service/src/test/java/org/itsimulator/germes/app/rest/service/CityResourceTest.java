package org.itsimulator.germes.app.rest.service;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.test.JerseyTest;
import org.itsimulator.germes.app.rest.dto.CityDTO;
import org.itsimulator.germes.app.rest.service.config.JerseyConfig;
import org.junit.Test;

public class CityResourceTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new JerseyConfig();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testFindCitiesSuccess() {
		List<Map<String, String>> cities = target("cities").request().get(List.class);	
		assertNotNull(cities);
		assertEquals(cities.size(), 1);
		
		Map<String, String> city = cities.get(0);
		assertEquals(city.get("name"), "Lviv");
	}
	
	@Test
	public void testFindCityByIdSuccess() {
		CityDTO city = target("cities/1").request().get(CityDTO.class);
		assertNotNull(city);
		assertEquals(city.getId(), 1);
		assertEquals(city.getName(), "Lviv");
	}
	
	@Test
	public void testFindCityByIdNotFound() {
		Response response = target("cities/2").request().get(Response.class);
		assertNotNull(response);
		assertEquals(response.getStatus(), Response.Status.NOT_FOUND.getStatusCode());
	}
	
	@Test
	public void findCityByIdInvalidId() {
		Response response = target("cities/aab").request().get(Response.class);
		assertNotNull(response);
		assertEquals(response.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());
	}
	
	@Test
	public void testSaveCitySuccess() {
		CityDTO city = new CityDTO();
		city.setName("Kiev");
		
		Response response = target("cities").request().post(Entity.entity(city, MediaType.APPLICATION_JSON));
		int code = response.getStatus();
		assertEquals(code, Response.Status.NO_CONTENT.getStatusCode());
	}
}
