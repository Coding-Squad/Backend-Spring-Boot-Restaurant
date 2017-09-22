package tn.esprit.rest;

import java.security.Principal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.entities.Orders;
import tn.esprit.entities.Rating;
import tn.esprit.entities.User;
import tn.esprit.services.RatingService;
import tn.esprit.services.UserService;

@RestController
@RequestMapping("/user")
public class UserRest {
	
	@Autowired
	UserService userService;
	
	@Autowired
	RatingService ratingService;
	
	@RequestMapping("/listUser")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<User> getListUser(){
		return userService.findAll();
	}
	
	@RequestMapping("/{id}")
	public User getUser(@PathVariable("id") Long id){
		User user = userService.findOne(id);
		return user;
	}
	
	@RequestMapping("/rating/")
	public Rating getRate(Principal principal){
		User user = userService.findByUsername(principal.getName());
		Rating rating = user.getRating();
		return rating;
	}
	
	@RequestMapping("/userRating/{id}")
	public Rating getUserRate(@PathVariable("id") Long id){
		User user = userService.findOne(id);
		Rating rating = user.getRating();
		return rating;
	}
	
	@RequestMapping(value="/rate", method=RequestMethod.POST)
	public ResponseEntity rate(@RequestBody Rating rating, Principal principal){
		
		User user = userService.findByUsername(principal.getName());
		Rating ratingAVG = ratingService.findOne(user.getId());
		ratingAVG.setFood(rating.getFood());
		ratingAVG.setService(rating.getService());
		ratingAVG.setPrice(rating.getPrice());
		user.setRating(ratingAVG);
		ratingAVG.setUser(user);
		userService.save(user);
		
		return new ResponseEntity("Rated!", HttpStatus.OK);
	}
	
}
