package DealerShip.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import DealerShip.controller.model.DealerShipData;
import DealerShip.controller.model.DealerShipData.DealerShipCustomer;
import DealerShip.controller.model.DealerShipData.DealerShipEmployee;
import DealerShip.controller.model.DealerShipData.DealerShipVehicle;
import DealerShip.dao.CustomerDao;
import DealerShip.dao.DealerShipDao;
import DealerShip.dao.EmployeeDao;
import DealerShip.dao.VehicleDao;
import DealerShip.entity.Customer;
import DealerShip.entity.DealerShip;
import DealerShip.entity.Employee;
import DealerShip.entity.Vehicle;

@Service
public class DealerShipService {

	@Autowired
	private DealerShipDao dealerShipDao;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private VehicleDao vehicleDao;

	//*****************Dealership CRUD operations*****************
	
	@Transactional(readOnly = false)
	public DealerShipData saveDealerShip(DealerShipData data) {
		Long DealerShipId = data.getDealerShipId();
		DealerShip dealerShip = findOrCreateDealerShip(DealerShipId, data);
		
		copyDealerShipFields(dealerShip, data);
		
		DealerShip savedDealerShip = dealerShipDao.save(dealerShip);
		return new DealerShipData(savedDealerShip);
	}

	private void copyDealerShipFields(DealerShip dealerShip, DealerShipData data) {
		dealerShip.setDealerShipName(data.getDealerShipName());
		dealerShip.setDealerShipAddress(data.getDealerShipAddress());
		dealerShip.setDealerShipState(data.getDealerShipState());
		dealerShip.setDealerShipZip(data.getDealerShipZip());
		dealerShip.setDealerShipPhone(data.getDealerShipPhone());		
	}

	private DealerShip findOrCreateDealerShip(Long dealerShipId, DealerShipData data) {
		if(Objects.isNull(dealerShipId)) {
			Optional<DealerShip> opDealerShipName = dealerShipDao.findByDealerShipName(data.getDealerShipName());
			Optional<DealerShip> opDealerShipAddress = dealerShipDao.findByDealerShipAddress(data.getDealerShipAddress());
			Optional<DealerShip> opDealerShipPhone = dealerShipDao.findByDealerShipPhone(data.getDealerShipPhone());
			
			if(opDealerShipName.isPresent()) {
				throw new DuplicateKeyException("Dealership with name " + data.getDealerShipName() + " already exsists.");
			}else if(opDealerShipAddress.isPresent()) {
				throw new DuplicateKeyException("Dealership with address " + data.getDealerShipAddress() + " already exsists.");
			}else if(opDealerShipPhone.isPresent()) {
				throw new DuplicateKeyException("Dealership with phone " + data.getDealerShipPhone() + " already exsists.");
			}
			return new DealerShip();
		}else {
			return findDealerShipById(dealerShipId);
		}
	}

	private DealerShip findDealerShipById(Long dealerShipId) {
		return dealerShipDao.findById(dealerShipId).orElseThrow(
				() -> new NoSuchElementException("Dealership with ID=" + dealerShipId + " not found."));
	}

	@Transactional(readOnly = true)
	public DealerShipData retrieveDealerShipById(Long dealerShipId) {
		DealerShip dealerShip = findDealerShipById(dealerShipId);
		dealerShip.getCustomers().clear();
		dealerShip.getEmployees().clear();
		dealerShip.getVehicles().clear();
		
		return new DealerShipData(dealerShip);
	}

	@Transactional(readOnly = false)
	public Map<String, String> deleteDealerShipById(Long dealerShipId) {
		DealerShip dealerShip = findDealerShipById(dealerShipId);
		dealerShipDao.delete(dealerShip);
		
		return Map.of(
				"message", "DealerShip with ID=" + dealerShipId + " was deleted successfully.");
	}
	
	//*****************Employee CRUD operations*****************
	
	@Transactional(readOnly = false)
	public DealerShipEmployee saveEmployee(DealerShipEmployee data, Long dealerShipId) {
		DealerShip dealerShip = findDealerShipById(dealerShipId);
		Long employeeId = data.getEmployeeId();
		Employee employee = findOrCreateEmployee(employeeId, dealerShipId, data.getEmployeeEmail(), data.getEmployeePhone());
		
		employee.setDealerShip(dealerShip);
		copyEmployeeFields(employee, data);
		
		dealerShip.getEmployees().add(employee);
		
		Employee savedEmployee = employeeDao.save(employee);
		return new DealerShipEmployee(savedEmployee);
	}

	private void copyEmployeeFields(Employee employee, DealerShipEmployee data) {
		employee.setEmployeeFirstName(data.getEmployeeFirstName());
		employee.setEmployeeLastName(data.getEmployeeLastName());
		employee.setEmployeePhone(data.getEmployeePhone());
		employee.setEmployeeEmail(data.getEmployeeEmail());
		employee.setEmployeeJobTittle(data.getEmployeeJobTittle());
	}

	private Employee findOrCreateEmployee(Long employeeId, Long dealerShipId, String employeeEmail, String employeePhone) {
		if(Objects.isNull(dealerShipId)) {
			new NoSuchElementException("Dealership with ID=" + dealerShipId + " not found");
			return null;
		}else {
		if (Objects.isNull(employeeId)) {
			Optional<Employee> opEmployeeEmail = employeeDao.findByEmployeeEmail(employeeEmail);
			Optional<Employee> opEmployeePhone = employeeDao.findByEmployeePhone(employeePhone);
			
			if(opEmployeeEmail.isPresent()) {
				throw new DuplicateKeyException("Employee with email " + employeeEmail + " already exsists.");
			}else if(opEmployeePhone.isPresent()) {
				throw new DuplicateKeyException("Employee with phone number " + employeePhone + " already exsists.");
			}
			return new Employee();
		} else {
			return findEmployeeById(employeeId);
		}}
	}

	private Employee findEmployeeById(Long employeeId) {
		return employeeDao.findById(employeeId).orElseThrow(
				() -> new NoSuchElementException("Employee with ID=" + employeeId + " not found."));
	}
	
	@Transactional(readOnly = false)
	public Map<String, String> deleteEmployee(Long dealerShipId, Long employeeId) {
		DealerShip dealerShip = findDealerShipById(dealerShipId);
		Employee employee = findEmployeeById(employeeId);
		
		employeeDao.delete(employee);
		dealerShip.getEmployees().remove(employee);
		
		return Map.of(
				"message", "Employee with ID=" + employeeId + " was deleted successfully.");
	}
	
	@Transactional(readOnly = true)
	public List<DealerShipEmployee> retrieveEmployees(Long dealerShipId) {
		DealerShip dealerShip = findDealerShipById(dealerShipId);
		List<DealerShipEmployee> employeeList = new LinkedList<>();
		
		for(Employee employee : dealerShip.getEmployees()) {
			DealerShipEmployee de = new DealerShipEmployee(employee);
			
			employeeList.add(de);
		}
		return employeeList;
	}

	@Transactional(readOnly = false)
	public DealerShipEmployee updateEmployee(DealerShipEmployee data, Long employeeId) {
		Employee employee = findEmployeeById(employeeId);
		copyEmployeeFields(employee, data);
		
		Employee savedEmployee = employeeDao.save(employee);
		
		return new DealerShipEmployee(savedEmployee);
	}


	//*****************Customer CRUD operations*****************
	
	@Transactional(readOnly = false)
	public DealerShipCustomer saveCustomer(DealerShipCustomer data, Long dealerShipId) {
		DealerShip dealerShip = findDealerShipById(dealerShipId);
		Long customerId = data.getCustomerId();
		Customer customer = findOrCreateCustomer(customerId, dealerShipId, data.getCustomerEmail());
		
		copyCustomerFields(customer, data);
		
		customer.getDealerShip().add(dealerShip);
		dealerShip.getCustomers().add(customer);
		
		Customer savedCustomer = customerDao.save(customer);
		
		return new DealerShipCustomer(savedCustomer);
	}

	private void copyCustomerFields(Customer customer, DealerShipCustomer data) {
		customer.setCustomerFirstName(data.getCustomerFirstName());
		customer.setCustomerLastName(data.getCustomerLastName());
		customer.setCustomerEmail(data.getCustomerEmail());
	}

	private Customer findOrCreateCustomer(Long customerId, Long dealerShipId, String customerEmail) {
		if(Objects.isNull(customerId)){
			Optional<Customer> opCustomerEmail = customerDao.findByCustomerEmail(customerEmail);
			
			if(opCustomerEmail.isPresent()) {
				throw new DuplicateKeyException("Customer with email " + customerEmail + " already exists.");
			}
			return new Customer();
		}else {
			return findCustomerById(customerId);
		}
	}

	private Customer findCustomerById(Long customerId) {
		return customerDao.findById(customerId).orElseThrow(
				() -> new NoSuchElementException("Customer with ID=" + customerId + " not found."));
	}
	
	@Transactional(readOnly = false)
	public Map<String, String> deleteCustomer(Long customerId) {
		List<DealerShip> dealerShip = dealerShipDao.findAll();
		Customer customer = findCustomerById(customerId);
		
		for(DealerShip dealerShips : dealerShip) {
			dealerShips.getCustomers().remove(customer);
		}
		customerDao.delete(customer);
		
		return Map.of(
				"message", "Customer with ID=" + customerId + " was deleted successfully");
	}

	@Transactional(readOnly = true)
	public List<DealerShipCustomer> retrieveCustomers(Long dealerShipId) {
		DealerShip dealerShip = findDealerShipById(dealerShipId);
		List<DealerShipCustomer> customerList = new LinkedList<>();
		
		for(Customer customer : dealerShip.getCustomers()) {
			DealerShipCustomer dc = new DealerShipCustomer(customer);
			
			customerList.add(dc);
		}
		return customerList;
	}

	@Transactional(readOnly = false)
	public DealerShipCustomer updateCustomer(DealerShipCustomer data, Long customerId) {
		Customer customer = findCustomerById(customerId);
		
		copyCustomerFields(customer, data);
		Customer savedCustomer = customerDao.save(customer);
		
		return new DealerShipCustomer(savedCustomer);
	}

	@Transactional(readOnly = false)
	public Map<String, String> addExsistingCustomerToDealerShip(Long dealerShipId, Long customerId) {
		DealerShip dealerShip = findDealerShipById(dealerShipId);
		Customer customer = findCustomerById(customerId);
		
		dealerShip.getCustomers().add(customer);
		return Map.of("message", "Customer with Id=" + customerId + " was added to Dealership with Id=" + dealerShipId);
	}


	//*****************Vehicle CRUD operations*****************
	
	@Transactional(readOnly = false)
	public DealerShipVehicle saveVehicle(DealerShipVehicle data, Long dealerShipId) {
		DealerShip dealerShip = findDealerShipById(dealerShipId);
		Long vehicleId = data.getVehicleId();
		Vehicle vehicle = findOrCreateVehicle(vehicleId, dealerShipId);
		
		copyVehicleFields(data, vehicle);
		
		vehicle.setDealerShip(dealerShip);
		dealerShip.getVehicles().add(vehicle);
		
		Vehicle savedVehicle = vehicleDao.save(vehicle);
		
		return new DealerShipVehicle(savedVehicle);
	}

	private void copyVehicleFields(DealerShipVehicle data, Vehicle vehicle) {
		vehicle.setVehicleYear(data.getVehicleYear());
		vehicle.setVehicleMake(data.getVehicleMake());
		vehicle.setVehicleModel(data.getVehicleModel());
		vehicle.setVehicleMilage(data.getVehicleMilage());
		vehicle.setVehiclePhysicalDamage(data.getVehiclePhysicalDamage());
		vehicle.setVehicleImage(data.getVehicleImage());
		vehicle.setVehicleType(data.getVehicleType());
	}

	private Vehicle findOrCreateVehicle(Long vehicleId, Long dealerShipId) {
		if(Objects.isNull(vehicleId)) {
			return new Vehicle();
		}else {
			return findVehicleById(vehicleId);
		}
	}

	private Vehicle findVehicleById(Long vehicleId) {
		return vehicleDao.findById(vehicleId).orElseThrow(
				()-> new NoSuchElementException("Vehicle with ID=" + vehicleId + " not found."));
	}

	@Transactional(readOnly = false)
	public Map<String, String> deleteVehicle(Long dealerShipId, Long vehicleId) {
		DealerShip dealerShip = findDealerShipById(dealerShipId);
		Vehicle vehicle = findVehicleById(vehicleId);
		
		vehicleDao.delete(vehicle);
		dealerShip.getVehicles().remove(vehicle);
		
		return Map.of(
				"message", "Vehicle with ID=" + vehicleId + " was deleted successfully.");
	}
	
	@Transactional(readOnly = true)
	public List<DealerShipVehicle> retrieveAllVehicles(Long dealerShipId) {
		DealerShip dealerShip = findDealerShipById(dealerShipId);
		List<DealerShipVehicle> vehicleList = new LinkedList<>();
		
		for(Vehicle vehicle : dealerShip.getVehicles()) {
			DealerShipVehicle dvd = new DealerShipVehicle(vehicle);
			
			vehicleList.add(dvd);
		}
		return vehicleList;
	}
	
	@Transactional(readOnly = false)
	public DealerShipVehicle updateVehicle(DealerShipVehicle data, Long vehicleId) {
		Vehicle vehicle = findVehicleById(vehicleId);
		copyVehicleFields(data, vehicle);
		
		Vehicle savedVehicle = vehicleDao.save(vehicle);
		
		return new DealerShipVehicle(savedVehicle);
	}
	}