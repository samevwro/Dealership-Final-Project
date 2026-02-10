package DealerShip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import DealerShip.controller.model.DealerShipData;
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
		log.info("Creating DealerShip {}", data);
		return dealerShipService.saveDealerShip(data);
	}


}
