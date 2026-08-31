package com.lcwd.hotel.controllers;

import com.lcwd.hotel.entities.Hotel;
import com.lcwd.hotel.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping("/save")
   public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel){
      Hotel savedHotel= this.hotelService.createHotel(hotel);
       return  ResponseEntity.status(HttpStatus.CREATED).body(savedHotel);
   }


    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> getHotel(@PathVariable String hotelId){
        Hotel savedHotel= this.hotelService.get(hotelId);
        return  ResponseEntity.status(HttpStatus.OK).body(savedHotel);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Hotel>> getAll(){
            return   ResponseEntity.ok(this.hotelService.getAll());
    }




}
