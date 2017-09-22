package tn.esprit.dao;

import org.springframework.data.repository.CrudRepository;

import tn.esprit.entities.DishToOrderItem;
import tn.esprit.entities.OrderItem;

public interface DishToOrderItemRepository extends CrudRepository<DishToOrderItem, Long> {
	
	void deleteByOrderItem(OrderItem orderItem);

}
