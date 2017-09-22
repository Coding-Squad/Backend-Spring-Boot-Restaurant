package tn.esprit.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.dao.RatingRepository;
import tn.esprit.entities.Rating;
import tn.esprit.services.RatingService;

@Service
public class RatingServiceImpl implements RatingService {
	
	@Autowired
	private RatingRepository ratingRepository;

	@Override
	public Rating save(Rating rating) {
		return ratingRepository.save(rating);
	}

	@Override
	public Rating findOne(Long id) {		
		return ratingRepository.findOne(id);
	}

}
