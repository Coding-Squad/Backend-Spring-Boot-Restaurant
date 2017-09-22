package tn.esprit.services;

import tn.esprit.entities.Rating;

public interface RatingService {
	
	Rating save(Rating rating);
	Rating findOne(Long id);

}
