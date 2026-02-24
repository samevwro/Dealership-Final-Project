package dealership.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import DealerShip.controller.model.DealerShipData;
import DealerShip.entity.DealerShip;
import DealerShip.service.DealerShipService;

public class DealerShipServiceTestSupport {
	private static final String DEALER_SHIP = "dealer_ship";

	// @formatter:off
	private DealerShipData insertDealerShip1 = new DealerShipData(
			1L,
			"AutoNation Ford",
			"1280 S, Wadsworth Ave",
			"Colorado",
			"80129",
			"(303) 548-2019"
			);
	
	private DealerShipData insertDealerShip2 = new DealerShipData(
			1L,
			"Urban Motors",
			"1980 W, Pine St",
			"Wyoming",
			"40981",
			"(520) 598-4823"
			);
	// @formatter:on
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DealerShipService dealerShipService;
	
	protected DealerShipData buildInsertDealerShip(int i) {
		if(i ==1) {
			return insertDealerShip1;
		}else {
			return insertDealerShip2;
		}
	}

	protected DealerShipData insertDealerShip(DealerShipData request) {
		DealerShip dealerShip = request.toDealerShip();
		DealerShipData clone = new DealerShipData(dealerShip);
		
		clone.setDealerShipId(null);		
		return dealerShipService.saveDealerShip(clone);
	}
	
	protected int rowsInLoactionTable() {
		return JdbcTestUtils.countRowsInTable(jdbcTemplate, DEALER_SHIP);
	}
}
