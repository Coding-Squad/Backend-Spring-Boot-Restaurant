package tn.esprit.dao;

import org.springframework.data.repository.CrudRepository;

import tn.esprit.entities.Dish;

public interface DishRepository extends CrudRepository<Dish, Long> {

}
