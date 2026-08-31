package com.lcwd.user.service;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.lcwd.user.service.entities.Hotel;
import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.external.services.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private RatingService ratingService;



	@Test
	void createRating(){
		Rating rating = new Rating("10","","",5,"hello",new Hotel());
		Rating savedRating=ratingService.createRating(rating);
		System.out.println(" new Rating Created");
	}


}
