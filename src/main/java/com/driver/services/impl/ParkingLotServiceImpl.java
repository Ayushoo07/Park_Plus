package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address)
    {
        ParkingLot obj=new ParkingLot();
        obj.setName(name);
        obj.setAddress(address);
        parkingLotRepository1.save(obj);
        return obj;
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour)
    {
        Spot spot=new Spot();
        ParkingLot parkingLot=parkingLotRepository1.findById(parkingLotId).get();
        spot.setParkingLot(parkingLot);

        spot.setPricePerHour(pricePerHour);
        if(numberOfWheels<=2)
        {
            spot.setSpotType(SpotType.TWO_WHEELER);
        }
        else if(numberOfWheels<=4)
        {
            spot.setSpotType(SpotType.FOUR_WHEELER);
        }
        else
        {
            spot.setSpotType(SpotType.OTHERS);
        }
        spot.setOccupied(false);
        parkingLot.getSpotList().add(spot);
        parkingLotRepository1.save(parkingLot);
        return spot;

    }

    @Override
    public void deleteSpot(int spotId)
    {
        Spot spot=spotRepository1.findById(spotId).get();
        ParkingLot parkingLot=spot.getParkingLot();
        parkingLot.getSpotList().remove(spot);
        spot.setParkingLot(null);
        spotRepository1.save(spot);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour)
    {
        Spot updateSpot=spotRepository1.findById(spotId).get();
        ParkingLot parkingLot=parkingLotRepository1.findById(parkingLotId).get();
        updateSpot.setParkingLot(parkingLot);
        parkingLot.getSpotList().add(updateSpot);
        updateSpot.setPricePerHour(pricePerHour);
        parkingLotRepository1.save(parkingLot);
        return updateSpot;

    }

    @Override
    public void deleteParkingLot(int parkingLotId)
    {
        ParkingLot parkingLot=parkingLotRepository1.findById(parkingLotId).get();
        List<Spot> spots=parkingLot.getSpotList();
        for (Spot spot : spots)
        {
            deleteSpot(spot.getId());
        }
        parkingLot.setSpotList(new ArrayList<>());
        parkingLotRepository1.delete(parkingLot);

    }
}
