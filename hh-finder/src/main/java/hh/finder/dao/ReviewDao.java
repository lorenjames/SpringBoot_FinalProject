package hh.finder.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hh.finder.entity.Review;

public interface ReviewDao extends JpaRepository<Review, Long> {

	List<Review> findByLocation_LocationId(Long locationId);

}
