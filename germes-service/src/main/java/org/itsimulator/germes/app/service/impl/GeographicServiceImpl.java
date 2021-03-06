package org.itsimulator.germes.app.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.itsimulator.germes.app.infra.util.CommonUtil;
import org.itsimulator.germes.app.model.entity.geography.City;
import org.itsimulator.germes.app.model.entity.geography.Station;
import org.itsimulator.germes.app.model.search.criteria.StationCriteria;
import org.itsimulator.germes.app.model.search.criteria.range.RangeCriteria;
import org.itsimulator.germes.app.service.GeographicService;

/**
 * Default implementation of the {@link GeographicService}
 * @author Morenets
 *
 */
public class GeographicServiceImpl implements GeographicService {
	/**
	 * Internal list of cities
	 */
	private final List<City> cities;
	
	private int counter = 0;
	
	private int stationCounter = 0;
	
	public GeographicServiceImpl() {
		cities = new ArrayList<City>();
	}

	@Override
	public List<City> findCities() {
		return CommonUtil.getSafeList(cities);
	}

	@Override
	public void saveCity(City city) {
		if(!cities.contains(city)) {
			city.setId(++counter);
			cities.add(city);
		}
		city.getStations().forEach((station) -> {
			if(station.getId() == 0) {
				station.setId(++stationCounter);
			}
		});
	}
	
	@Override
	public Optional<City> findCityById(final int id) {
		return cities.stream().filter((city) -> city.getId() == id).findFirst();
	}
	
	@Override
	public List<Station> searchStations(final StationCriteria criteria, final RangeCriteria rangeCriteria) {
		
		Set<Station> stations = new HashSet<>();
		for(City city : cities) {
			stations.addAll(city.getStations());
		}
		return stations.stream().filter((station) -> station.match(criteria)).collect(Collectors.toList());
	}

}
