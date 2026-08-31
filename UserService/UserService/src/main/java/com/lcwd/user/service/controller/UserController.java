package com.lcwd.user.service.controller;


import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.impl.UserServiceImpl;
import com.lcwd.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/save")
    public ResponseEntity<User> createUser(@RequestBody User user){

        User savedUser = this.userService.save(user);
        return  ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
    int retryCount=1;

    @GetMapping("/{userId}")
    //@CircuitBreaker(name="ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
    //@Retry(name="ratingHotelService", fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name="userRateLimiter" , fallbackMethod = "ratingHotelFallback")
    public  ResponseEntity<User> getSingleUser(@PathVariable String userId){
        logger.info("Retry Count:{}",retryCount);
        retryCount++;
        User user =this.userService.getUser(userId);
        return  ResponseEntity.ok(user);

    }

    //creating fall back method for circuit breaker

    public ResponseEntity<User>  ratingHotelFallback(String userId,Exception ex){

        User user = new User(UUID.randomUUID().toString(),"srinu",
                "dummy@gmail.com",
                Arrays.asList(new Rating()),
                "This is a dummy created user becuase some service is down");
        logger.info("Fallback is executed because service is down"+ex.getMessage());

        return new ResponseEntity<>(user,HttpStatus.OK);

    }



    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> userList=this.userService.getAllUser();
        return ResponseEntity.ok(userList);
    }



}
