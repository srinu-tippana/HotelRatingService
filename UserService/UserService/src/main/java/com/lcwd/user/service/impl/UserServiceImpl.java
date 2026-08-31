package com.lcwd.user.service.impl;

import com.lcwd.user.service.entities.Hotel;
import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.exceptions.ResourceNotFoundException;
import com.lcwd.user.service.external.services.HotelService;
import com.lcwd.user.service.repository.UserRepository;
import com.lcwd.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User save(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return  userRepository.save(user);
    }
    @Override
    public List<User> getAllUser() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        // get User from database with the help of user repository
        User user= this.userRepository.findById(userId).orElseThrow(()->  new ResourceNotFoundException("User with given id is not found on server bro"+userId));
        //  Here the ratings are not present so we will fetch those ratings
        // we have to call this url
        // http://localhost:8883/ratings/users/f3de8cd7-2432-43e8-aa6b-0a9c866dc9e1
       Rating[] ratingsOfUser= restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+userId, Rating[].class);
       logger.info("{} ",ratingsOfUser);
       // Basically we got all the ratings of the user based  on the userId and  that rating also has a field called as hotelId now withing the users ratings
        // we will get the hotel values and then set it into the rating
      List<Rating> ratingList= Arrays.asList(ratingsOfUser).stream().map(rating -> {
          //http://localhost:8882/hotels/b7552563-de9b-4e43-96f1-b902cb56b6a2
          //ResponseEntity<Hotel> forEntity=restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
          // the above one is the normal resttemplate call and the bottom one is the modern feign template call
          Hotel hotel = hotelService.getHotel(rating.getHotelId());
          rating.setHotel(hotel);
          return  rating;
      }).collect(Collectors.toList());
       user.setRatings(ratingList);
        return  user;
    }

    @Override
    public void  deleteUser(String userId) {
        User user = this.userRepository.findById(userId).orElseThrow(()->  new ResourceNotFoundException("User with given id is not found on server"+userId));

          this.userRepository.delete(user);
    }

    @Override
    public User updateUser(String userId) {
        return null;
    }
}
