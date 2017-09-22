package tn.esprit.services.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.dao.OrderListRepository;
import tn.esprit.entities.OrderItem;
import tn.esprit.entities.OrderList;
import tn.esprit.services.OrderItemService;
import tn.esprit.services.OrderListService;

@Service
public class OrderListServiceImpl implements OrderListService {
	
	@Autowired
	private OrderItemService orderItemService;
	
	@Autowired
	private OrderListRepository orderListRepository;

	@Override
	public void clearOrderList(OrderList orderList) {
		
		List<OrderItem> orderitemList = orderItemService.findByOrderList(orderList);
		
		for(OrderItem orderitem: orderitemList){
			orderitem.setOrderList(null);
			orderItemService.save(orderitem);
		}
		
		orderList.setGrandTotal(new BigDecimal(0));
		
		orderListRepository.save(orderList);
		
	}

	@Override
	public OrderList updateOrderList(OrderList orderList) {
		BigDecimal orderTotal = new BigDecimal(0);
		
		List<OrderItem> orderItemList = orderItemService.findByOrderList(orderList);
		
		for (OrderItem orderItem : orderItemList){
			orderItemService.updateOrderitem(orderItem);
			orderTotal = orderTotal.add(orderItem.getSubTotal());
		}
		
		orderList.setGrandTotal(orderTotal);
		orderListRepository.save(orderList);
		
		return orderList;
	}

}
