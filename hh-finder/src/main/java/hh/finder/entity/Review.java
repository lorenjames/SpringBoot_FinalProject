package hh.finder.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reviewId;
	private String review;

	@ManyToOne
	@JoinColumn(name = "location_id", nullable = false)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Location location;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private User user;

}
