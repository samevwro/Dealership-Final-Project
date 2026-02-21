package dealership.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import DealerShip.DealerShipApplication;


@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = DealerShipApplication.class)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:schema.sql"})
class DealerShipControllerTest {

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
