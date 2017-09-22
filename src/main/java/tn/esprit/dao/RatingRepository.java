package tn.esprit.dao;

import org.springframework.data.repository.CrudRepository;

import tn.esprit.entities.Rating;

public interface RatingRepository extends CrudRepository<Rating, Long> {

}
