package hh.finder.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import hh.finder.entity.Feature;

public interface FeatureDao extends JpaRepository<Feature, Long> {

}
