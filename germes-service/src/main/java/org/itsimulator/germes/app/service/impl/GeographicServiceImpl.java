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
			cities.add(city);
		}
	}
	
	@Override
	public Optional<City> findCityById(final int id) {
		return cities.stream().filter((city) -> city.getId() == id).findFirst();
	}
	
	@Override
	public List<Station> searchStations(final StationCriteria criteria, final RangeCriteria rangeCriteria) {
		
		Stream<City> stream = cities.stream().filter(
				(city) -> StringUtils.isEmpty(criteria.getName()) || city.getName().equals(criteria.getName()));
		Optional<Set<Station>> stations = stream.map((city) -> city.getStations()).reduce((stations1, stations2) -> {
			Set<Station> newStations = new HashSet<>(stations1);
			newStations.addAll(stations2);
			return newStations;
		});
		
		if(!stations.isPresent()) {
			return Collections.emptyList();
		}
		return stations.get()
				.stream()
				.filter((station) -> criteria.getTransportType() == null
					|| station.getTransportType() == criteria.getTransportType()).collect(Collectors.toList());
	}

}
