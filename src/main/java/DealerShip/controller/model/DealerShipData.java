package DealerShip.controller.model;

import java.util.HashSet;
import java.util.Set;

import DealerShip.entity.Customer;
import DealerShip.entity.DealerShip;
import DealerShip.entity.Employee;
import DealerShip.entity.Vehicle;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DealerShipData {

	private Long dealerShipId;
	private String dealerShipName;
	private String dealerShipAddress;
	private String dealerShipState;
	private String dealerShipZip;
	private String dealerShipPhone;

	Set<DealerShipVehicle> vehicles = new HashSet<>();
	Set<DealerShipCustomer> customers = new HashSet<>();
	Set<DealerShipEmployee> employees = new HashSet<>();

	public DealerShipData(DealerShip dealerShip) {
		dealerShipId = dealerShip.getDealerShipId();
		dealerShipName = dealerShip.getDealerShipName();
		dealerShipAddress = dealerShip.getDealerShipAddress();
		dealerShipState = dealerShip.getDealerShipState();
		dealerShipZip = dealerShip.getDealerShipZip();
		dealerShipPhone = dealerShip.getDealerShipPhone();
		
		for(Vehicle vehicle : dealerShip.getVehicles()) {
			vehicles.add(new DealerShipVehicle(vehicle));
		}
		
		for(Customer customer : dealerShip.getCustomers()) {
			customers.add(new DealerShipCustomer(customer));
		}
		
		for(Employee employee : dealerShip.getEmployees()) {
			employees.add(new DealerShipEmployee(employee));
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class DealerShipCustomer {
		
		private Long customerId;
		private String customerFirstName;
		private String customerLastName;
		private String customerEmail;
		
		public DealerShipCustomer(Customer customer) {
			customerId = customer.getCustomerId();
			customerFirstName = customer.getCustomerFirstName();
			customerLastName = customer.getCustomerLastName();
			customerEmail = customer.getCustomerEmail();
		}
	}

	@Data
	@NoArgsConstructor
	public static class DealerShipEmployee {
		private Long employeeId;
		private String employeeFirstName;
		private String employeeLastName;
		private String employeePhone;
		private String employeeEmail;
		private String employeeJobTittle;
		
		public DealerShipEmployee(Employee employee) {
			employeeId = employee.getEmployeeId();
			employeeFirstName = employee.getEmployeeFirstName();
			employeeLastName = employee.getEmployeeLastName();
			employeePhone = employee.getEmployeePhone();
			employeeEmail = employee.getEmployeeEmail();
			employeeJobTittle = employee.getEmployeeJobTittle();
		}
	}

	@Data
	@NoArgsConstructor
	public static class DealerShipVehicle {
		private Long vehicleId;
		private Integer vehicleYear;
		private String vehicleMake;
		private String vehicleModel;
		private String vehicleMilage;
		private String vehiclePhysicalDamage;
		private String vehicleImage;
		private String vehicleType;
		
		public DealerShipVehicle(Vehicle vehicle) {
			vehicleId = vehicle.getVehicleId();
			vehicleYear = vehicle.getVehicleYear();
			vehicleMake = vehicle.getVehicleMake();
			vehicleModel = vehicle.getVehicleModel();
			vehicleMilage = vehicle.getVehicleMilage();
			vehiclePhysicalDamage = vehicle.getVehiclePhysicalDamage();
			vehicleImage = vehicle.getVehicleImage();
			vehicleType = vehicle.getVehicleType();
		}
	}
}