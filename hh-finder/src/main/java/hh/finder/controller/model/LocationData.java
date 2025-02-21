package hh.finder.controller.model;

import java.util.HashSet;
import java.util.Set;

import hh.finder.entity.Feature;
import hh.finder.entity.Location;
import hh.finder.entity.Review;
import hh.finder.entity.Special;
import hh.finder.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocationData {

	private Long locationId;
	private String businessName;
	private String streetAddress;
	private String city;
	private String state;
	private String zip;
	private String phone;
	private Set<FeatureData> features = new HashSet<>();
	private Set<SpecialsData> specials = new HashSet<>();
	private Set<ReviewsData> reviews = new HashSet<>();

	public LocationData(Location location) {
		this.locationId = location.getLocationId();
		this.businessName = location.getBusinessName();
		this.streetAddress = location.getStreetAddress();
		this.city = location.getCity();
		this.state = location.getState();
		this.zip = location.getZip();
		this.phone = location.getPhone();

		for (Feature feature : location.getFeatures()) {
			this.features.add(new FeatureData(feature));
		}

		for (Special special : location.getSpecials()) {
			this.specials.add(new SpecialsData(special));
		}

		for (Review review : location.getReviews()) {
			this.reviews.add(new ReviewsData(review));
		}
	}

	public LocationData(Location location, Special specialDay) {
		this.locationId = location.getLocationId();
		this.businessName = location.getBusinessName();
		this.streetAddress = location.getStreetAddress();
		this.city = location.getCity();
		this.state = location.getState();
		this.zip = location.getZip();
		this.phone = location.getPhone();

		for (Feature feature : location.getFeatures()) {
			this.features.add(new FeatureData(feature));
		}

		for (Special special : location.getSpecials()) {
			this.specials.add(new SpecialsData(special));
		}

		for (Review review : location.getReviews()) {
			this.reviews.add(new ReviewsData(review));
		}
	}

	public Location toLocation() {
		Location location = new Location();

		location.setLocationId(locationId);
		location.setBusinessName(businessName);
		location.setStreetAddress(streetAddress);
		location.setCity(city);
		location.setState(state);
		location.setZip(zip);
		location.setPhone(phone);

		for (FeatureData featureData : features) {
			location.getFeatures().add(featureData.toFeature());
		}
		for (SpecialsData specialData : specials) {
			location.getSpecials().add(specialData.toSpecial());
		}
		for (ReviewsData reviewData : reviews) {
			location.getReviews().add(reviewData.toReview(null));
		}

		return location;
	}

	public Location toLocation(User user) {
		Location location = new Location();

		location.setLocationId(locationId);
		location.setBusinessName(businessName);
		location.setStreetAddress(streetAddress);
		location.setCity(city);
		location.setState(state);
		location.setZip(zip);
		location.setPhone(phone);

		if (features != null) {
			for (FeatureData featureData : features) {
				if (featureData != null) {
					location.getFeatures().add(featureData.toFeature());
				}
			}
		}

		if (specials != null) {
			for (SpecialsData specialData : specials) {
				if (specialData != null) {
					location.getSpecials().add(specialData.toSpecial());
				}
			}
		}

		if (reviews != null && user != null) {
			for (ReviewsData reviewData : reviews) {
				if (reviewData != null) {
					location.getReviews().add(reviewData.toReview(user));
				}
			}
		}

		return location;
	}

	@Data
	@NoArgsConstructor
	public static class FeatureData {
		private Long featureId;
		private String feature;

		public FeatureData(Feature feature) {
			this.featureId = feature.getFeatureId();
			this.feature = feature.getFeatureName();
		}

		public Feature toFeature() {
			Feature features = new Feature();

			features.setFeatureId(featureId);
			features.setFeatureName(feature);

			return features;
		}
	}

	@Data
	@NoArgsConstructor
	public static class SpecialsData {
		private Long specialsId;
		private String special;
		private double price;
		private String day;
		private String hours;
		private int dayInt;

		public SpecialsData(Special special) {
			this.specialsId = special.getSpecialsId();
			this.special = special.getSpecial();
			this.price = special.getPrice();
			this.day = special.getDay();
			this.hours = special.getHours();
			this.dayInt = special.getDayInt();
		}

		public Special toSpecial() {
			Special specials = new Special();

			specials.setSpecialsId(specialsId);
			specials.setSpecial(special);
			specials.setPrice(price);
			specials.setDay(day);
			specials.setHours(hours);
			specials.setDayInt(dayInt);

			return specials;

		}
	}

	@Data
	@NoArgsConstructor
	public static class ReviewsData {
		private Long reviewsId;
		private String review;
		private Long userId;

		public ReviewsData(Review review) {
			this.reviewsId = review.getReviewId();
			this.review = review.getReview();
			this.userId = review.getUser() != null ? review.getUser().getUserId() : null;
		}

		public Review toReview(User user) {
			if (user == null) {
				throw new IllegalArgumentException("A valid user is required to create a review.");
			}

			Review reviews = new Review();
			reviews.setReviewId(reviewsId);
			reviews.setReview(review);
			reviews.setUser(user);

			return reviews;
		}
	}

}
