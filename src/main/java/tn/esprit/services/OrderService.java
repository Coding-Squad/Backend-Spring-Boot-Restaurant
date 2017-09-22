package tn.esprit.services;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import tn.esprit.entities.OrderList;
import tn.esprit.entities.Orders;
import tn.esprit.entities.User;

public interface OrderService {

	Orders createOrder(OrderList orderList, User user);
	Orders findOne(Long id);
	void rateOrder(Orders orders);
	List<Object> visit(String datetime);
	List<Object> numVisit(DateTime datetime);
}
