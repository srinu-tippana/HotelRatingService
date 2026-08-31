package com.lcwd.rating.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user_ratings")
public class Rating {


    @Id
    private  String ratingId;

    private String userId;

    private  String hotelId;

    private int rating;

    private  String feedback;

    public Rating() {
    }

    public Rating(String userId, String ratingId, int rating, String hotelId, String feedback) {
        this.userId = userId;
        this.ratingId = ratingId;
        this.rating = rating;
        this.hotelId = hotelId;
        this.feedback = feedback;
    }

    public String getRatingId() {
        return ratingId;
    }

    public void setRatingId(String ratingId) {
        this.ratingId = ratingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
