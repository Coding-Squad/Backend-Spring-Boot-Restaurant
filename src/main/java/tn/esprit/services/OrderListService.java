package tn.esprit.services;

import tn.esprit.entities.OrderList;

public interface OrderListService {
	
	void clearOrderList(OrderList orderList);
	
	OrderList updateOrderList(OrderList orderList);

}
