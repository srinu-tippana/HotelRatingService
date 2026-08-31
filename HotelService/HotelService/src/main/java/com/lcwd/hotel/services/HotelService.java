package com.lcwd.hotel.services;

import com.lcwd.hotel.entities.Hotel;

import java.util.List;

public interface HotelService {


    //create

    Hotel createHotel(Hotel hotel);


    //get All

    List<Hotel> getAll();


    //get Single

    Hotel get(String id);


}
