package DealerShip.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class DealerShip {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long dealerShipId;
	
	private String dealerShipName;
	private String dealerShipAddress;
	private String dealerShipState;
	private String dealerShipZip;
	private String dealerShipPhone;
	
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "dealerShip", cascade = CascadeType.ALL, orphanRemoval = true)
	Set<Vehicle> vehicles = new HashSet<>();

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "DealerShip_Customer", joinColumns = @JoinColumn(name ="dealer_ship_Id"), inverseJoinColumns = @JoinColumn(name ="customer_Id"))
	Set<Customer> customers = new HashSet<>();

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "dealerShip", cascade = CascadeType.ALL, orphanRemoval = true)
	Set<Employee> employees = new HashSet<>();
}
