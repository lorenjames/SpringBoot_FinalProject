package hh.finder.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hh.finder.controller.model.LocationData;
import hh.finder.entity.Location;

public interface LocationDao extends JpaRepository<Location, Long> {

	List<LocationData> findAllByCityIgnoreCase(String city);

}
