package DealerShip.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import DealerShip.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

}
