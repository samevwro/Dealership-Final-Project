package DealerShip.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import DealerShip.entity.Vehicle;

public interface VehicleDao extends JpaRepository<Vehicle, Long> {

}
