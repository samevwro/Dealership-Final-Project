package DealerShip.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import DealerShip.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {

}
