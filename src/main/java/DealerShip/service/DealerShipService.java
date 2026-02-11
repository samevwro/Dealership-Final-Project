package DealerShip.service;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import DealerShip.controller.model.DealerShipData;
import DealerShip.controller.model.DealerShipData.DealerShipCustomer;
import DealerShip.controller.model.DealerShipData.DealerShipEmployee;
import DealerShip.dao.CustomerDao;
import DealerShip.dao.DealerShipDao;
import DealerShip.dao.EmployeeDao;
import DealerShip.dao.VehicleDao;
import DealerShip.entity.Customer;
import DealerShip.entity.DealerShip;
import DealerShip.entity.Employee;

@Service
public class DealerShipService {

	@Autowired
	private DealerShipDao dealerShipDao;
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private VehicleDao vehicleDao;

	@Transactional(readOnly = false)
	public DealerShipData saveDealerShip(DealerShipData data) {
		Long DealerShipId = data.getDealerShipId();
		DealerShip dealerShip = findOrCreateDealerShip(DealerShipId);
		
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

	private DealerShip findOrCreateDealerShip(Long dealerShipId) {
		if(Objects.isNull(dealerShipId)) {
			return new DealerShip();
		}else {
			return findDealerShipById(dealerShipId);
		}
	}

	private DealerShip findDealerShipById(Long dealerShipId) {
		return dealerShipDao.findById(dealerShipId).orElseThrow(
				() -> new NoSuchElementException("Dealership with ID=" + dealerShipId + " not found."));
	}

	public DealerShipEmployee saveEmployee(DealerShipEmployee data, Long dealerShipId) {
		DealerShip dealerShip = findDealerShipById(dealerShipId);
		Long employeeId = data.getEmployeeId();
		Employee employee = findOrCreateEmployee(employeeId, dealerShipId);
		
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

	private Employee findOrCreateEmployee(Long employeeId, Long dealerShipId) {
		if(Objects.isNull(dealerShipId)) {
			new NoSuchElementException("Dealership with ID=" + dealerShipId + " not found");
			return null;
		}else {
		if (Objects.isNull(employeeId)) {
			return new Employee();
		} else {
			return findEmployeeById(employeeId);
		}}
	}

	private Employee findEmployeeById(Long employeeId) {
		return employeeDao.findById(employeeId).orElseThrow(
				() -> new NoSuchElementException("Employee with ID=" + employeeId + " not found."));
	}

	public DealerShipCustomer saveCustomer(DealerShipCustomer data, Long dealerShipId) {
		DealerShip dealerShip = findDealerShipById(dealerShipId);
		Long customerId = data.getCustomerId();
		Customer customer = findOrCreateCustomer(customerId, dealerShipId);
		
		
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

	private Customer findOrCreateCustomer(Long customerId, Long dealerShipId) {
		if(Objects.isNull(customerId)){
			return new Customer();
		}else {
			return findCustomerById(customerId);
		}
	}

	private Customer findCustomerById(Long customerId) {
		return customerDao.findById(customerId).orElseThrow(
				() -> new NoSuchElementException("Customer with ID=" + customerId + " not found."));
	}
	
}
