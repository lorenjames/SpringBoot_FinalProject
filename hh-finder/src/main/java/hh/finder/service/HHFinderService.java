package hh.finder.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hh.finder.controller.model.LocationData;
import hh.finder.controller.model.LocationData.ReviewsData;
import hh.finder.controller.model.LocationData.SpecialsData;
import hh.finder.dao.FeatureDao;
import hh.finder.dao.LocationDao;
import hh.finder.dao.ReviewDao;
import hh.finder.dao.SpecialDao;
import hh.finder.dao.UserDao;
import hh.finder.entity.Location;
import hh.finder.entity.Review;
import hh.finder.entity.Special;
import hh.finder.entity.User;

@Service
public class HHFinderService {

	@Autowired
	private LocationDao locationDao;
	@Autowired
	private FeatureDao featureDao;
	@Autowired
	private ReviewDao reviewDao;
	@Autowired
	private SpecialDao specialDao;
	@Autowired
	private UserDao userDao;

	@Transactional(readOnly = false)
	public LocationData saveLocation(LocationData locationData) {
		Location location = locationData.toLocation();
		Location dbLocation = locationDao.save(location);

		return new LocationData(dbLocation);
	}

	@Transactional(readOnly = true)
	public List<LocationData> retrieveAllocations() {
		return locationDao.findAll().stream()
				.sorted((loc1, loc2) -> loc1.getBusinessName().compareTo(loc2.getBusinessName())).map(LocationData::new)
				.toList();
	}

	@Transactional(readOnly = true)
	public List<LocationData> retrieveHHLocationbyCity(String city) {

		List<LocationData> locations = locationDao.findAllByCityIgnoreCase(city);

		if (locations.isEmpty()) {
			throw new NoSuchElementException("No locations found in " + city);
		}
		return locationDao.findAllByCityIgnoreCase(city).stream().toList();

	}

	@Transactional(readOnly = true)
	public LocationData retrieveHHLocationById(Long locationId) {
		Location location = findHHLocationById(locationId);
		return new LocationData(location);
	}

	@Transactional(readOnly = true)
	private Location findHHLocationById(Long locationId) {
		return locationDao.findById(locationId)
				.orElseThrow(() -> new NoSuchElementException("Location with ID=" + locationId + " was not found."));
	}

	@Transactional(readOnly = true)
	public List<LocationData> retrieveLocationsByDayInt(int dayInt) {
		List<Special> specials = specialDao.findByDayInt(dayInt);

		return specials.stream().map(special -> {
			Location location = special.getLocation();
			LocationData locationData = new LocationData(location);

			Set<SpecialsData> filteredSpecials = locationData.getSpecials().stream()
					.filter(s -> s.getDayInt() == dayInt).collect(Collectors.toSet());

			locationData.setSpecials(filteredSpecials);
			return locationData;
		}).distinct().toList();
	}

	@Transactional(readOnly = true)
	public List<LocationData> retrieveLocationsByDay(String day) {
		List<Special> specials = specialDao.findByDayIgnoreCase(day);

		return specials.stream().map(special -> {
			Location location = special.getLocation();
			LocationData locationData = new LocationData(location);

			Set<SpecialsData> filteredSpecials = locationData.getSpecials().stream()
					.filter(s -> s.getDay().equalsIgnoreCase(day)).collect(Collectors.toSet());

			locationData.setSpecials(filteredSpecials);
			return locationData;

		}).distinct().toList();
	}

	@Transactional(readOnly = true)
	public Set<ReviewsData> retrieveHHReviewById(Long locationId) {
		List<Review> reviews = reviewDao.findByLocation_LocationId(locationId);

		if (reviews.isEmpty()) {
			throw new NoSuchElementException("No reviews found for locationId: " + locationId);
		}

		return reviews.stream().map(ReviewsData::new).collect(Collectors.toSet());
	}

	@Transactional(readOnly = false)
	public ReviewsData saveReview(Long locationId, Long userId, ReviewsData reviewData) {
		Location location = findHHLocationById(locationId);

		User user = findUserById(userId);
		if (user == null) {
			throw new NoSuchElementException("User not found with ID: " + userId);
		}

		Review review = reviewData.toReview(user);
		review.setLocation(location);

		Review savedReview = reviewDao.save(review);

		return new ReviewsData(savedReview);
	}

	public User findUserById(Long userId) {
		return userDao.findById(userId)
				.orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));
	}

	@Transactional(readOnly = false)
	public ReviewsData updateReview(Long reviewId, ReviewsData reviewData) {

		Review review = reviewDao.findById(reviewId)
				.orElseThrow(() -> new NoSuchElementException("Review not found with ID: " + reviewId));

		review.setReview(reviewData.getReview());

		Review updatedReview = reviewDao.save(review);

		return new ReviewsData(updatedReview);
	}

	@Transactional(readOnly = false)
	public void deleteReviewById(Long reviewId) {
		Review review = findReviewById(reviewId);
		reviewDao.delete(review);

	}

	private Review findReviewById(Long reviewId) {
		return reviewDao.findById(reviewId)
				.orElseThrow(() -> new NoSuchElementException("Review with ID=" + reviewId + " was not found."));
	}

}
