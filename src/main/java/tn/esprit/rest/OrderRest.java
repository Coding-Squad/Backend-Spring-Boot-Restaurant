package tn.esprit.rest;

import java.security.Principal;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.entities.Dish;
import tn.esprit.entities.OrderItem;
import tn.esprit.entities.OrderList;
import tn.esprit.entities.Orders;
import tn.esprit.entities.User;
import tn.esprit.services.DishService;
import tn.esprit.services.OrderItemService;
import tn.esprit.services.OrderListService;
import tn.esprit.services.OrderService;
import tn.esprit.services.UserService;

@RestController
@RequestMapping("/order")
public class OrderRest {
	
	private Orders orders = new Orders();
	
	@Autowired
	private DishService dishService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderItemService orderItemService;
	
	@Autowired
	private OrderListService orderListService;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/add")
	public ResponseEntity addItem(
			@RequestBody HashMap<String, String> mapper, Principal principal
			){
		String dishId = (String) mapper.get("dishId");
		String qty = (String) mapper.get("qty");
	
		User user = userService.findByUsername(principal.getName());
		Dish dish = dishService.findOne(Long.parseLong(dishId));
		
		OrderItem orderItem = orderItemService.addDishToOrderList(dish, user, Integer.parseInt(qty));
		
		return new ResponseEntity("Dish added successfully!", HttpStatus.OK);
	}
	
	@RequestMapping("/getOrderItemList")
	public List<OrderItem> getOrderItemList(Principal principal){
		
		User user = userService.findByUsername(principal.getName());
		OrderList orderList = user.getOrderList();
		
		List<OrderItem> orderItemList = orderItemService.findByOrderList(orderList);
		
		orderListService.updateOrderList(orderList);
		
		return orderItemList;
	}
	
	@RequestMapping("/getOrderList")
	public OrderList getOrderList(Principal principal){
		
		User user = userService.findByUsername(principal.getName());
		OrderList orderList = user.getOrderList();
		
		orderListService.updateOrderList(orderList);
		
		return orderList;
	}
	
	@RequestMapping("/removeItem")
	public ResponseEntity removeItem(@RequestBody String id){
		orderItemService.removeOrderItem(orderItemService.findById(Long.parseLong(id)));
		
		return new ResponseEntity("Order item removed successfully", HttpStatus.OK);
	}
	@RequestMapping("/updateOrderItem")
	public ResponseEntity updateOrderItem(
			@RequestBody HashMap<String, String> mapper
			){
		String orderItemId = mapper.get("orderItemId");
		String qty = mapper.get("qty");
		
		OrderItem orderItem = orderItemService.findById(Long.parseLong(orderItemId));
		orderItem.setQty(Integer.parseInt(qty));
		
		orderItemService.updateOrderitem(orderItem);
		
		return new ResponseEntity("Order item updated successfully", HttpStatus.OK);

}
	@RequestMapping("/rate")
	public ResponseEntity rateOrder(
			@RequestBody HashMap<String, String> mapper
			){
		String orderId = mapper.get("orderId");
		String rating = mapper.get("rating");
		
		Orders orders = orderService.findOne(Long.parseLong(orderId));
		orders.setRating(Integer.parseInt(rating));
		
		orderService.rateOrder(orders);
		
		return new ResponseEntity("Order rated successfully", HttpStatus.OK);
	}
	
	@RequestMapping(value="/confirm", method=RequestMethod.POST)
	public Orders confirmOrder(
			Principal principal
			){
		
		OrderList orderList = userService.findByUsername(principal.getName()).getOrderList();
		List<OrderItem> orderItemList = orderItemService.findByOrderList(orderList);
		User user = userService.findByUsername(principal.getName());
		Orders orders = orderService.createOrder(orderList, user);
		
		orderListService.clearOrderList(orderList);
		
		this.orders=orders;
		
		return orders;
	}
	
	@RequestMapping("/getOrderListSummary")
	public List<Orders> getOrderListSummary(Principal principal){
		User user = userService.findByUsername(principal.getName());
		List<Orders> orderListSummary = user.getOrdersList();
		return orderListSummary;
	}
	
	@RequestMapping(value="/visit", method=RequestMethod.GET)
	public List<Object> getVisitNumb(DateTime datetime){
		return orderService.numVisit(datetime);
	}
	
	@RequestMapping(value="/visite", method=RequestMethod.POST)
	public List<Object> getVisitNumbe(@RequestBody String datetime){
		return orderService.visit(datetime);
	}
	
}
