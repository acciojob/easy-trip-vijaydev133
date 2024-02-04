package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import com.google.common.collect.HashBiMap;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AirportRepository {
    public HashMap<String, Airport> Airportdb = new HashMap<>();
     public HashMap<Integer, Flight> Flightdb = new HashMap<>();
    public HashMap<Integer,Passenger> Passengerdb =new HashMap<>();
     public HashMap<Integer, List<Integer>> FlightToPassengerdb= new HashMap<>();

    public void addAirport(Airport airport) {
        String t = airport.getAirportName();
        Airportdb.put(t, airport);
    }

    public String getLargestAirportName() {
        int max = Integer.MIN_VALUE;
        String t = "";
        for (Airport s : Airportdb.values()) {
            if (s.getNoOfTerminals() > max) {
                max = s.getNoOfTerminals();
                t = s.getAirportName();
            } else if (s.getNoOfTerminals() == max) {
                if (s.getAirportName().compareTo(t) < 0) {
                    t = s.getAirportName();
                }
            }
        }

        return t;
    }

    public void addFlight(Flight flight) {
        Integer i = flight.getFlightId();
        Flightdb.put(i, flight);
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {
        Double duration = Double.MAX_VALUE;
        for (Flight f : Flightdb.values()) {
            if ((f.getFromCity().equals(fromCity)) && (f.getToCity().equals(toCity))) {
                if (f.getDuration() < duration) {
                    duration = f.getDuration();
                }

            }
        }
        if(duration==Double.MAX_VALUE){
            return -1;
        }
        return duration;
    }

    public void addPassenger(Passenger passenger) {
        Integer i = passenger.getPassengerId();
        Passengerdb.put(i, passenger);
    }

    public int getNumberOfPeopleOn(Date date, String airportName) {
        Airport airport = Airportdb.get(airportName);
        if(airport==null){
            return 0;
        }
        City city = airport.getCity();
        int count = 0;
        for(Flight f:Flightdb.values()){
            if(date.equals(f.getFlightDate()))
                if(f.getToCity().equals(city)||f.getFromCity().equals(city)){

                    int flightId = f.getFlightId();
                    count = count + FlightToPassengerdb.get(flightId).size();
                }
        }
        return count;
    }

    public int calculateFlightFare(Integer flightId) {
        int noOfPeopleBooked = FlightToPassengerdb.get(flightId).size();
        return noOfPeopleBooked*50 + 3000;
    }

    public String bookATicket(Integer flightId, Integer passengerId) {
        if(FlightToPassengerdb.get(flightId)!=null &&(FlightToPassengerdb.get(flightId).size()<Flightdb.get(flightId).getMaxCapacity())){


            List<Integer> passengers =  FlightToPassengerdb.get(flightId);

            if(passengers.contains(passengerId)){
                return "FAILURE";
            }

            passengers.add(passengerId);
            FlightToPassengerdb.put(flightId,passengers);
            return "SUCCESS";
        }
        else if(FlightToPassengerdb.get(flightId)==null)
        {
            FlightToPassengerdb.put(flightId,new ArrayList<>());
            List<Integer> passengers =  FlightToPassengerdb.get(flightId);

            if(passengers.contains(passengerId)){
                return "FAILURE";
            }

            passengers.add(passengerId);
            FlightToPassengerdb.put(flightId,passengers);
            return "SUCCESS";

        }
        return "FAILURE";
    }

    public String cancelATicket(Integer flightId, Integer passengerId) {
        List<Integer> passengers = FlightToPassengerdb.get(flightId);
        if(passengers == null){
            return "FAILURE";
        }

        if(passengers.contains(passengerId)){
            passengers.remove(passengerId);
            return "SUCCESS";
        }
        return "FAILURE";
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
        int count = 0;
        for(Map.Entry<Integer,List<Integer>> entry: FlightToPassengerdb.entrySet()){

            List<Integer> passengers  = entry.getValue();
            for(Integer passenger : passengers){
                if(passenger==passengerId){
                    count++;
                }
            }
        }
        return count;
    }

    public String getAirportNameFromFlightId(Integer flightId) {
        if(Flightdb.containsKey(flightId)){
            City city = Flightdb.get(flightId).getFromCity();
            for(Airport airport:Airportdb.values()){
                if(airport.getCity().equals(city)){
                    return airport.getAirportName();
                }
            }
        }
        return null;
    }

    public int calculateRevenueOfAFlight(Integer flightId) {
        int noOfPeopleBooked = FlightToPassengerdb.get(flightId).size();
        int totalRevenue = (25 * noOfPeopleBooked * noOfPeopleBooked) + (2975 * noOfPeopleBooked);

        return totalRevenue;
    }
}