package org.itsimulator.germes.app.model.entity.geography;

import org.itsimulator.germes.app.model.entity.transport.TransportType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Contains unit-tests to check functionality of {@link City} class
 * 
 * @author Morenets
 *
 */
public class CityTest {
	private City city;
	
	@Before
	public void setup() {
		city = new City("Odesa");
	}
	
	@Test
	public void testAddValidStationSuccess() {
		Station station = city.addStation(TransportType.AUTO);
		Station station2 = city.addStation(TransportType.AVIA);

		assertTrue(containsStation(city, station));
		assertEquals(city, station.getCity());
		assertEquals(city.getStations().size(), 2);
	}

	@Test(expected=NullPointerException.class)
	public void testAddStationNullTransportTypeFailure() {
		city.addStation(null);

		assertTrue(false);
	}

	@Test
	public void testRemoveStationSuccess() {
		Station station = city.addStation(TransportType.AVIA);
		
		city.removeStation(station);

		assertTrue(city.getStations().isEmpty());
	}

	@Test(expected=NullPointerException.class)
	public void testRemoveNullStationFailure() {
		city.removeStation(null);

		assertTrue(false);
	}
	
	private boolean containsStation(City city, Station station) {
		return city.getStations().contains(station);
	}

}
