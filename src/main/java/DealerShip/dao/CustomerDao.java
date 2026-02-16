package DealerShip.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import DealerShip.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {

	Optional<Customer> findByCustomerEmail(String customerEmail);

}
