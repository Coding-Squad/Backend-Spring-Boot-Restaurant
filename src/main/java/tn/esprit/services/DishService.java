package tn.esprit.services;

import java.util.List;

import tn.esprit.entities.Dish;

public interface DishService {
	
	List<Dish> findAll();
	List<Dish> customList(Long id);
	List<Dish> statData();
	List<Dish> statLabel();
	Dish findOne(Long id);
	Dish save(Dish dish);
	void removeOne(Long id);

}
