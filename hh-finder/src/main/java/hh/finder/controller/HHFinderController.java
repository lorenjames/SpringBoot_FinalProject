package hh.finder.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import hh.finder.controller.model.LocationData;
import hh.finder.controller.model.LocationData.ReviewsData;
import hh.finder.service.HHFinderService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/hh_finder")
@Slf4j
public class HHFinderController {

	@Autowired
	private HHFinderService hhFinderService;

	@PostMapping("/location")
	@ResponseStatus(code = HttpStatus.CREATED)
	public LocationData createLocation(@RequestBody LocationData locationData) {
		log.info("Creating new Business Location{}", locationData);
		return hhFinderService.saveLocation(locationData);
	}

	@GetMapping("/location")
	public List<LocationData> retrieveAllHHLocations() {
		log.info("Retrieving all Business Locations.");
		return hhFinderService.retrieveAllocations();
	}

	@GetMapping("/location/{locationId}")
	public LocationData retrieveHHLocation(@PathVariable Long locationId) {
		log.info("Retrieving Business Location with ID={}", locationId);
		return hhFinderService.retrieveHHLocationById(locationId);
	}

	@GetMapping("/location/city/{city}")
	public List<LocationData> retrieveHHLocationByCity(@PathVariable String city) {

		if (hhFinderService.retrieveHHLocationbyCity(city) == null) {
			throw new IllegalArgumentException("Threre are no locations for " + city + ", please enter a valid city.");
		}

		log.info("Retrieving Business Locations in {}", city);
		return hhFinderService.retrieveHHLocationbyCity(city);
	}

	@GetMapping("/location/specials/{dayInt}")
	public List<LocationData> retrieveLocationsBySpecialDay(@PathVariable int dayInt) {
		log.info("Retrieving Business Locations with specials on dayInt={}", dayInt);
		return hhFinderService.retrieveLocationsByDayInt(dayInt);
	}

	@GetMapping("/location/specials/day/{day}")
	public List<LocationData> retrieveLocationBySpecialDayName(@PathVariable String day) {
		log.info("Retrieving Business locations with specials on day={}", day);
		return hhFinderService.retrieveLocationsByDay(day);
	}

	@GetMapping("/location/reviews/{locationId}")
	public Set<ReviewsData> retrieveHHReviews(@PathVariable Long locationId) {
		log.info("Retrieving reviews for location ID={}", locationId);
		return hhFinderService.retrieveHHReviewById(locationId);
	}

	@PostMapping("/locations/{locationId}/reviews")
	public ReviewsData addReviewToLocation(@PathVariable Long locationId, @RequestBody ReviewsData reviewData) {

		if (reviewData.getUserId() == null) {
			throw new IllegalArgumentException("User ID must be provided for a review.");
		}

		log.info("Adding review to location ID={}", locationId);

		return hhFinderService.saveReview(locationId, reviewData.getUserId(), reviewData);
	}

	@PutMapping("/reviews/{reviewId}")
	public ReviewsData updateReview(@PathVariable Long reviewId, @RequestBody ReviewsData reviewData) {

		reviewData.setReviewsId(reviewId);

		if (reviewData.getReviewsId() == null) {
			throw new IllegalArgumentException("Review ID must be provided.");
		}

		log.info("Updating review ID={}", reviewId);

		return hhFinderService.updateReview(reviewId, reviewData);

	}

	@DeleteMapping("/reviews/delete/{reviewId}")
	public Map<String, String> deleteHHReviewById(@PathVariable Long reviewId) {
		log.info("Deleting review with ID={}", reviewId);

		hhFinderService.deleteReviewById(reviewId);

		return Map.of("message", "Review with ID=" + reviewId + " deleted.");
	}

}
