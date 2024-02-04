package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AirportService {
    AirportRepository airportRepositorye=new AirportRepository();
    public void addAirport(Airport airport) {

        airportRepositorye.addAirport(airport);
    }
    public String getLargestAirportName(){
        return airportRepositorye.getLargestAirportName();
    }

    public void addFlight(Flight flight) {
        airportRepositorye.addFlight(flight);
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {
        return airportRepositorye.getShortestDurationOfPossibleBetweenTwoCities( fromCity,  toCity);
    }

    public void addPassenger(Passenger passenger) {
        airportRepositorye.addPassenger(passenger);
    }

    public int getNumberOfPeopleOn(Date date, String airportName) {
        return airportRepositorye.getNumberOfPeopleOn(date,airportName);
    }

    public int calculateFlightFare(Integer flightId) {
        return airportRepositorye.calculateFlightFare(flightId);
    }

    public String bookATicket(Integer flightId, Integer passengerId) {
        return airportRepositorye.bookATicket(flightId,passengerId);
    }

    public String cancelATicket(Integer flightId, Integer passengerId) {
        return  airportRepositorye.cancelATicket( flightId , passengerId);
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
        return airportRepositorye.countOfBookingsDoneByPassengerAllCombined( passengerId);
    }

    public String getAirportNameFromFlightId(Integer flightId) {
        return airportRepositorye.getAirportNameFromFlightId(flightId);
    }

    public int calculateRevenueOfAFlight(Integer flightId) {
        return airportRepositorye.calculateRevenueOfAFlight(flightId);
    }
}