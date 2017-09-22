package tn.esprit.services;

import java.util.List;

import tn.esprit.entities.Dish;
import tn.esprit.entities.OrderItem;
import tn.esprit.entities.OrderList;
import tn.esprit.entities.User;

public interface OrderItemService {
	
	OrderItem addDishToOrderList(Dish dish, User user, int qty);
	
	List<OrderItem> findByOrderList(OrderList orderList);
	
	OrderItem updateOrderitem(OrderItem orderItem);
	
	void removeOrderItem(OrderItem orderItem);
	
	OrderItem findById (Long id);
	
	OrderItem save(OrderItem orderItem);

}
