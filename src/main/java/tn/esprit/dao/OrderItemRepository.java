package tn.esprit.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import tn.esprit.entities.OrderItem;
import tn.esprit.entities.OrderList;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long>{

	List<OrderItem> findByOrderList(OrderList orderList);
	
}
