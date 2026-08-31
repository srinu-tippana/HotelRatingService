package com.lcwd.rating.services;

import com.lcwd.rating.entities.Rating;

import java.util.List;

public interface RatingService{

    //create
    Rating create(Rating rating);

    //getAllratings
    List<Rating> getRatings();

    //get all by UserId
    List<Rating> getRatingByUserId(String userId);

    // get all by hotel

    List<Rating> getRatingByHotelId(String hotelId);


}
