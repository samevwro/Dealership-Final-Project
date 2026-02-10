package DealerShip.service;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import DealerShip.controller.model.DealerShipData;
import DealerShip.dao.CustomerDao;
import DealerShip.dao.DealerShipDao;
import DealerShip.dao.EmployeeDao;
import DealerShip.dao.VehicleDao;
import DealerShip.entity.DealerShip;

@Service
public class DealerShipService {

	@Autowired
	private DealerShipDao dealerShipDao;
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private VehicleDao vehicleDao;

	public DealerShipData saveDealerShip(DealerShipData data) {
		Long DealerShipId = data.getDealerShipId();
		DealerShip dealerShip = findOrCreateDealerShip(DealerShipId);
		
		copyDealerShipFields(dealerShip, data);
		
		DealerShip savedDealerShip = dealerShipDao.save(dealerShip);
		return new DealerShipData(savedDealerShip);
	}

	private void copyDealerShipFields(DealerShip dealerShip, DealerShipData data) {
		dealerShip.setDealerShipName(data.getDealerShipName());
		dealerShip.setDealerShipAddress(data.getDealerShipAddress());
		dealerShip.setDealerShipState(data.getDealerShipState());
		dealerShip.setDealerShipZip(data.getDealerShipZip());
		dealerShip.setDealerShipPhone(data.getDealerShipPhone());		
	}

	private DealerShip findOrCreateDealerShip(Long dealerShipId) {
		if(Objects.isNull(dealerShipId)) {
			return new DealerShip();
		}else {
			return findDealerShipById(dealerShipId);
		}
	}

	private DealerShip findDealerShipById(Long dealerShipId) {
		return dealerShipDao.findById(dealerShipId).orElseThrow(
				() -> new NoSuchElementException("Dealership with ID=" + dealerShipId + " not found."));
	}
	
}
