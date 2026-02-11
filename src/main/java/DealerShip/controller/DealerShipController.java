package DealerShip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import DealerShip.service.DealerShipService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/dealership")
@Slf4j
public class DealerShipController {
	@Autowired
	private DealerShipService dealerShipService;
	
	
	@PostMapping("/dealership_post")
	@ResponseStatus(HttpStatus.CREATED)
	public DealerShipData insertDealerShip(@RequestBody DealerShipData data) {
		log.info("Creating Dealership {}", data);
		return dealerShipService.saveDealerShip(data);
	}
	
	@PutMapping("/dealership_put/{dealerShipId}")
	@ResponseStatus(HttpStatus.OK)
	public DealerShipData updateDealerShip(@RequestBody DealerShipData data, @PathVariable Long dealerShipId) {
		data.setDealerShipId(dealerShipId);
		log.info("Updating Dealership with id={}", dealerShipId);
		return dealerShipService.saveDealerShip(data);
	}
	
	@PostMapping("/{dealerShipId}/employee")
	@ResponseStatus(HttpStatus.CREATED)
	public DealerShipEmployee insertEmployee(@RequestBody DealerShipEmployee data, @PathVariable Long dealerShipId) {
		log.info("Creating Employee at Dealership={}", dealerShipId);
		return dealerShipService.saveEmployee(data, dealerShipId);
	}
	
	@PostMapping("/{dealerShipId}/customer")
	@ResponseStatus(HttpStatus.CREATED)
	public DealerShipCustomer insertCustomer(@RequestBody DealerShipCustomer data, @PathVariable Long dealerShipId) {
		log.info("Creating Customer at Dealership={}", dealerShipId);
		return dealerShipService.saveCustomer(data, dealerShipId);
	}


}
