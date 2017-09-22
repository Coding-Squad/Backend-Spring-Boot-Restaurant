package tn.esprit.services.impl;

import java.math.BigDecimal;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.dao.OrderRepository;
import tn.esprit.entities.Dish;
import tn.esprit.entities.OrderItem;
import tn.esprit.entities.OrderList;
import tn.esprit.entities.Orders;
import tn.esprit.entities.User;
import tn.esprit.services.OrderItemService;
import tn.esprit.services.OrderService;
import tn.esprit.services.UserService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemService orderItemService;
	
	@Autowired
	private UserService userService;

	@Override
	public Orders createOrder(OrderList orderList, User user) {
		
		Orders orders = new Orders();
		
		List<OrderItem> orderItemList = orderItemService.findByOrderList(orderList);
		
		for (OrderItem orderitem : orderItemList){
			Dish dish = orderitem.getDish();
			orderitem.setOrders(orders);
		}
		
		orders.setOrderItemList(orderItemList);
		orders.setOrderDate(Calendar.getInstance().getTime());
		orders.setOrdertotal(orderList.getGrandTotal());
		orders.setUser(user);
		orders = orderRepository.save(orders);
		
		return orders;
	}

	@Override
	public Orders findOne(Long id) {
		return orderRepository.findOne(id);
	}

	@Override
	public void rateOrder(Orders orders) {
		orderRepository.save(orders);
		
	}

	@Override
	public List<Object> visit(String datetime) {	
		List<Object> num = new ArrayList<>();
		StringBuilder build = new StringBuilder(datetime);
		build.deleteCharAt(0);
		build.deleteCharAt(24);		
		datetime = build.toString();
		DateTime dt = ISODateTimeFormat.dateTime().parseDateTime(datetime);
		
					for (int i=0; i<= 23; i++)	{
						
					System.out.println("time is"+dt.plusHours(i).toDate());
					System.out.println("tIME is"+build);				
					
					TypedQuery<Object> query = em.createNamedQuery(Orders.QUERY_COUNT_ORDER, Object.class);
					query.setParameter(1, dt.plusHours(i).toDate(), TemporalType.TIMESTAMP);
					query.setParameter(2, dt.plusHours(i+1).toDate(), TemporalType.TIMESTAMP);				
					num.add(i, query.getResultList());
					System.out.println(num.get(i));
					
					}
					return num;				
		
	}

	@Override
	public List<Object> numVisit(DateTime datetime) {
		List<Object> num = new ArrayList<>();
		for (int i=0; i<= 23; i++)	{
		
		TypedQuery<Object> query = em.createNamedQuery(Orders.QUERY_COUNT_ORDER, Object.class);
		query.setParameter(1, datetime.toDate(), TemporalType.TIMESTAMP);
		query.setParameter(2, datetime.plusHours(1).toDate(), TemporalType.TIMESTAMP);				
		num.add(i, query.getResultList());
		
		}
		return num;	
	}

	

	

	

	
}
