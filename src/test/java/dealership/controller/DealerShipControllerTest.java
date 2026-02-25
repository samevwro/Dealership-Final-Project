package dealership.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import DealerShip.DealerShipApplication;
import DealerShip.controller.model.DealerShipData;


@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = DealerShipApplication.class)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:Schema.sql"})
@SqlConfig(encoding = "utf-8")
class DealerShipControllerTest extends DealerShipServiceTestSupport {

	@Test
	void testInsertDealerShip() {
		//Given: A dealership create request
		DealerShipData request = buildInsertDealerShip(1);
		DealerShipData expected = buildInsertDealerShip(1);
		
		//When: The dealershipData is added to the dealerShip table
		DealerShipData actual = insertDealerShip(request);
		
		//Then: the dealerShip returned is whats expected
		assertThat(actual).isEqualTo(expected);
		
		//And: there is a new dealership entity
		assertThat(rowsInLoactionTable()).isOne();
	}
	
	@Test
	void testRetrieveDealerShip() {
		//Given: A dealership
		DealerShipData dealerShip = insertDealerShip(buildInsertDealerShip(1));
		DealerShipData expected = buildInsertDealerShip(1);
		
		//When: The location is retrieved by id
		DealerShipData actual = retrieveDealerShip(dealerShip.getDealerShipId());
		
		//Then: The actual location is equal to the expected location
		assertThat(actual).isEqualTo(expected);
		
	}
}