package DealerShip.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import DealerShip.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

	Optional<Employee> findByEmployeeEmail(String employeeEmail);

	Optional<Employee> findByEmployeePhone(String employeePhone);

}
