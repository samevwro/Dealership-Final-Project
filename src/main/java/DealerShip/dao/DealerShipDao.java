package DealerShip.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import DealerShip.entity.DealerShip;

public interface DealerShipDao extends JpaRepository<DealerShip, Long> {

	Optional<DealerShip> findByDealerShipName(String dealerShipName);

	Optional<DealerShip> findByDealerShipAddress(String dealerShipAddress);

	Optional<DealerShip> findByDealerShipPhone(String dealerShipPhone);

}
