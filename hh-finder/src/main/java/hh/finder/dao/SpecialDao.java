package hh.finder.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hh.finder.entity.Special;

public interface SpecialDao extends JpaRepository<Special, Long> {

	List<Special> findByDayInt(int dayInt);

	List<Special> findByDayIgnoreCase(String day);

}
