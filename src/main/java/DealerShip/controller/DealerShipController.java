package DealerShip.controller;

import java.util.List;
import java.util.Map;

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

import DealerShip.controller.model.DealerShipData;
import DealerShip.controller.model.DealerShipData.DealerShipCustomer;
import DealerShip.controller.model.DealerShipData.DealerShipEmployee;
import DealerShip.controller.model.DealerShipData.DealerShipVehicle;
import DealerShip.service.DealerShipService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/dealership")
@Slf4j
public class DealerShipController {
	@Autowired
	private DealerShipService dealerShipService;

	//*****************Dealership CRUD operations*****************

	@PostMapping("/dealership_post")
	@ResponseStatus(HttpStatus.CREATED)
	public DealerShipData insertDealerShip(@RequestBody DealerShipData data) {
		log.info("Creating Dealership {}", data);
		return dealerShipService.saveDealerShip(data);
	}//good

	@PutMapping("/dealership_put/{dealerShipId}")
	@ResponseStatus(HttpStatus.OK)
	public DealerShipData updateDealerShip(@RequestBody DealerShipData data, @PathVariable Long dealerShipId) {
		data.setDealerShipId(dealerShipId);
		log.info("Updating Dealership with id={}", dealerShipId);
		return dealerShipService.saveDealerShip(data);
	}//good

	@DeleteMapping("/dealership_delete/{dealerShipId}")
	public Map<String, String> deleteDealership(@PathVariable Long dealerShipId) {
		log.info("Deleting Dealership with ID={}", dealerShipId);
		return dealerShipService.deleteDealerShipById(dealerShipId);
	}//good

	@GetMapping("/{dealerShipId}")
	public DealerShipData retrieveDealerShip(@PathVariable Long dealerShipId) {
		log.info("Retrieving DealerShip with ID={}", dealerShipId);
		return dealerShipService.retrieveDealerShipById(dealerShipId);
	}//good

	//*****************Employee CRUD operations*****************

	@PostMapping("/{dealerShipId}/employee")
	@ResponseStatus(HttpStatus.CREATED)
	public DealerShipEmployee insertEmployee(@RequestBody DealerShipEmployee data, @PathVariable Long dealerShipId) {
		log.info("Creating Employee at Dealership with ID={}", dealerShipId);
		return dealerShipService.saveEmployee(data, dealerShipId);
	}//good

	@GetMapping("/{dealerShipId}/employee")
	public List<DealerShipEmployee> retrieveAllEmployeeAtDealer(@PathVariable Long dealerShipId) {
		log.info("Retrieving employee at Dealership with ID={}", dealerShipId);
		return dealerShipService.retrieveEmployees(dealerShipId);
	}//good

	@PutMapping("/employee/{employeeId}")
	public DealerShipEmployee updateEmployee(@RequestBody DealerShipEmployee data, @PathVariable Long employeeId) {
		log.info("Updating employee with ID={}", employeeId);
		return dealerShipService.updateEmployee(data, employeeId);
	}//good

	@DeleteMapping("/{dealerShipId}/employee/{employeeId}")
	public Map<String, String> deleteEmployee(@PathVariable Long dealerShipId, @PathVariable Long employeeId) {
		log.info("Deleting employee at Dealership with ID={}", dealerShipId);
		return dealerShipService.deleteEmployee(dealerShipId, employeeId);
	}//good

	//*****************Customer CRUD operations*****************

	@PostMapping("/{dealerShipId}/customer")
	@ResponseStatus(HttpStatus.CREATED)
	public DealerShipCustomer insertCustomer(@RequestBody DealerShipCustomer data, @PathVariable Long dealerShipId) {
		log.info("Creating Customer at Dealership={}", dealerShipId);
		return dealerShipService.saveCustomer(data, dealerShipId);
	}//good

	@GetMapping("/{dealerShipId}/customer")
	public List<DealerShipCustomer> retrieveCustomerByDealerShip(@PathVariable Long dealerShipId) {
		log.info("Retrieveing all customers at DealerShip with ID={}", dealerShipId);
		return dealerShipService.retrieveCustomers(dealerShipId);
	}//good
	
	//This Api call is to ONLY add an existing customer to a new dealership, the new dealership that the customer 
	//needs to be added to is put into the url where dealerShipId is
	@PutMapping("/{dealerShipId}/addCustomerToDealer/{customerId}")
	public Map<String, String> addExsistingCustomerToDealerShip(@PathVariable Long dealerShipId, @PathVariable Long customerId){
		log.info("Adding customer with ID={}, to dealership with ID={}", customerId, dealerShipId);
		return dealerShipService.addExsistingCustomerToDealerShip(dealerShipId, customerId);
	}//good
	
	//This Api call is ONLY to alter customer information
	@PutMapping("/customer/{customerId}")
	public DealerShipCustomer updateCustomer(@RequestBody DealerShipCustomer data, @PathVariable Long customerId) {
		log.info("Updating customer with ID={}", customerId);
		return dealerShipService.updateCustomer(data, customerId);
	}//

	@DeleteMapping("/{dealerShipId}/customer/{customerId}")
	public Map<String, String> deleteCustomer(@PathVariable Long customerId) {
		log.info("Deleting customer with ID={}", customerId);
		return dealerShipService.deleteCustomer(customerId);
	}//good

	//*****************Vehicle CRUD operations*****************

	@PostMapping("/{dealerShipId}/vehicle")
	@ResponseStatus(HttpStatus.CREATED)
	public DealerShipVehicle insertVehicle(@RequestBody DealerShipVehicle data, @PathVariable Long dealerShipId) {
		log.info("Creating Vehicle at DealerShip={}", dealerShipId);
		return dealerShipService.saveVehicle(data, dealerShipId);
	}//good

	@GetMapping("/{dealerShipId}/vehicle")
	public List<DealerShipVehicle> retrieveAllVehicles(@PathVariable Long dealerShipId) {
		log.info("Retrieving all vehicles fron Dealership with ID={}", dealerShipId);
		return dealerShipService.retrieveAllVehicles(dealerShipId);
	}//good

	@PutMapping("/vehicle/{vehicleId}")
	public DealerShipVehicle updateVehicle(@RequestBody DealerShipVehicle data, @PathVariable Long vehicleId) {
		log.info("Updating vehicle with ID={}", vehicleId);
		return dealerShipService.updateVehicle(data, vehicleId);
	}//good

	@DeleteMapping("/{dealerShipId}/vehicle/{vehicleId}")
	public Map<String, String> deleteVehicle(@PathVariable Long dealerShipId, @PathVariable Long vehicleId) {
		log.info("Deleting vehicle at DealerShip={}", dealerShipId);
		return dealerShipService.deleteVehicle(dealerShipId, vehicleId);
	}//good
}
