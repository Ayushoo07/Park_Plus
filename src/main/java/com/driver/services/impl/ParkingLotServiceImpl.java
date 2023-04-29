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
        spotRepository1.save(spot);
        return spot;

    }

    @Override
    public void deleteSpot(int spotId)
    {
        Spot spot=spotRepository1.findById(spotId).get();
        ParkingLot parkingLot=spot.getParkingLot();
        parkingLot.getSpotList().remove(spot);
        spotRepository1.delete(spot);
        parkingLotRepository1.save(parkingLot);

    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour)
    {
        Spot updateSpot=spotRepository1.findById(spotId).get();
        ParkingLot parkingLot=updateSpot.getParkingLot();
        parkingLot.getSpotList().remove(updateSpot);
        updateSpot.setPricePerHour(pricePerHour);
        parkingLot.getSpotList().add(updateSpot);
        spotRepository1.save(updateSpot);

        return updateSpot;

    }

    @Override
    public void deleteParkingLot(int parkingLotId)
    {
        ParkingLot parkingLot=parkingLotRepository1.findById(parkingLotId).get();
        for(Spot spot:parkingLot.getSpotList())
        {
            spotRepository1.delete(spot);
        }
        parkingLotRepository1.delete(parkingLot);

    }
}
