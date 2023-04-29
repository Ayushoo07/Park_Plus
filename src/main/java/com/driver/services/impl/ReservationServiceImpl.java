package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
        User user;
        try{
            user =userRepository3.findById(userId).get();
        }
        catch (Exception e)
        {
            throw new Exception("Cannot make reservation");
        }
        ParkingLot parkingLot;
        try {
            parkingLot=parkingLotRepository3.findById(parkingLotId).get();
        }
        catch (Exception e)
        {
            throw new Exception("Cannot make reservation");
        }

        int minPrice=Integer.MAX_VALUE;
        Spot reqSpot=null;

        for (Spot spot: parkingLot.getSpotList())
        {
            SpotType spotType=spot.getSpotType();
            int wheels=0;
            if (spotType.equals(SpotType.TWO_WHEELER))
            {
                wheels=2;
            }
            else if(spotType.equals(SpotType.FOUR_WHEELER))
            {
                wheels=4;
            }
            else
            {
                wheels=Integer.MAX_VALUE;
            }

            if(spot.getOccupied()==false && numberOfWheels<=wheels && spot.getPricePerHour()<minPrice )
            {
                minPrice=spot.getPricePerHour();
                reqSpot=spot;
            }
        }

        if (reqSpot==null)
            throw new Exception("Cannot make reservation");

        reqSpot.setOccupied(true);
        Reservation reservation=new Reservation();
        reservation.setSpot(reqSpot);
        reservation.setNumberOfHours(timeInHours);
        reservation.setUser(user);
        user.getReservationList().add(reservation);
        userRepository3.save(user);
        spotRepository3.save(reqSpot);

        return reservation;
    }
}
