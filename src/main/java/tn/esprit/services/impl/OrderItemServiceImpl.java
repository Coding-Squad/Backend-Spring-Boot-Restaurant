package tn.esprit.services.impl;

import java.math.BigDecimal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.dao.DishToOrderItemRepository;
import tn.esprit.dao.OrderItemRepository;
import tn.esprit.entities.Dish;
import tn.esprit.entities.DishToOrderItem;
import tn.esprit.entities.OrderItem;
import tn.esprit.entities.OrderList;
import tn.esprit.entities.User;
import tn.esprit.services.OrderItemService;

@Service
public class OrderItemServiceImpl implements OrderItemService {
	
	@Autowired
	private OrderItemRepository orderitemRepository;
	
	@Autowired
	private DishToOrderItemRepository dishToOrderitemRepository;

	@Override
	public OrderItem addDishToOrderList(Dish dish, User user, int qty) {
		
		List<OrderItem> orderItemList = findByOrderList(user.getOrderList());
		
		for (OrderItem orderItem : orderItemList){
			if (dish.getId() == orderItem.getDish().getId()){
				orderItem.setQty(orderItem.getQty()+qty);
				orderItem.setSubTotal(new BigDecimal(dish.getPrice()).multiply(new BigDecimal(qty)));
				orderitemRepository.save(orderItem);
				return orderItem;
			}
		}
		
		OrderItem orderItem = new OrderItem();
		orderItem.setOrderList(user.getOrderList());
		orderItem.setDish(dish);
		orderItem.setQty(qty);
		orderItem.setSubTotal(new BigDecimal(dish.getPrice()).multiply(new BigDecimal(qty)));
		
		orderItem = orderitemRepository.save(orderItem);
		
		DishToOrderItem dishToOrderItem = new DishToOrderItem();
		dishToOrderItem.setDish(dish);
		dishToOrderItem.setOrderItem(orderItem);
		dishToOrderitemRepository.save(dishToOrderItem);
		
		return orderItem;	
	}

	@Override
	public List<OrderItem> findByOrderList(OrderList orderList) {
		return orderitemRepository.findByOrderList(orderList);
	}

	@Override
	public OrderItem updateOrderitem(OrderItem orderItem) {
		
		BigDecimal bigDecimal = new BigDecimal(orderItem.getDish().getPrice()).multiply(new BigDecimal(orderItem.getQty()));
		bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		orderItem.setSubTotal(bigDecimal);
		
		orderitemRepository.save(orderItem);
		
		return orderItem;
	}

	@Override
	@Transactional
	public void removeOrderItem(OrderItem orderItem) {
		dishToOrderitemRepository.deleteByOrderItem(orderItem);
		orderitemRepository.delete(orderItem);
		
	}

	@Override
	public OrderItem findById(Long id) {
		return orderitemRepository.findOne(id);
		}

	@Override
	public OrderItem save(OrderItem orderItem) {
		return orderitemRepository.save(orderItem);
	}

}
