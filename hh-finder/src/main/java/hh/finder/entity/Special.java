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
public class Special {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long specialsId;
	private String special;
	private double price;
	private String day;
	private String hours;
	private int dayInt;

	@ManyToOne
	@JoinColumn(name = "location_id", nullable = false)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Location location;

}
