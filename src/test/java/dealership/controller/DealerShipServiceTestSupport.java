package dealership.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import DealerShip.controller.model.DealerShipData;
import DealerShip.controller.model.DealerShipData.DealerShipVehicle;
import DealerShip.entity.DealerShip;
import DealerShip.entity.Vehicle;
import DealerShip.service.DealerShipService;

public class DealerShipServiceTestSupport {
	private static final String VEHICLE = "vehicle";

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
	
	private DealerShipVehicle insertVehicle1 = new DealerShipVehicle(
			1L,
			2013,
			"Ford",
			"F-150",
			"123,900",
			"Dent on rear tailgate",
			"",
			"Truck"
			);
	private DealerShipVehicle insertVehicle2 = new DealerShipVehicle(
			2L,
			2007,
			"Honda",
			"Accord",
			"180,720",
			"The passenger mirror is cracked and lark scratch across the passenger front door",
			"",
			"Sedan"
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
	
	protected int rowsInDealerShipTable() {
		return JdbcTestUtils.countRowsInTable(jdbcTemplate, DEALER_SHIP);
	}
	
	protected DealerShipData retrieveDealerShip(Long dealerShipId) {
		return dealerShipService.retrieveDealerShipById(dealerShipId);
	}
	protected int rowsInVehicleTable() {
		return JdbcTestUtils.countRowsInTable(jdbcTemplate, VEHICLE);
	}

	protected DealerShipVehicle insertVehicle(DealerShipVehicle insertVehicle, DealerShipData dealerShip) {
		Vehicle vehicle = insertVehicle.toVehicle();
		DealerShipVehicle clone = new DealerShipVehicle(vehicle);
		
		clone.setVehicleId(null);
		return dealerShipService.saveVehicle(clone, dealerShip.getDealerShipId());
	}

	protected DealerShipVehicle buildInsertVehicle(int i) {
		if(i == 1) {
			return insertVehicle1;
		}else {
			return insertVehicle2;
		}
	}
	protected void deleteDealerShip(Long dealerShipId) {
		dealerShipService.deleteDealerShipById(dealerShipId);
	}

}
