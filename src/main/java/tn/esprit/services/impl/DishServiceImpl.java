package tn.esprit.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.dao.DishRepository;
import tn.esprit.entities.Dish;
import tn.esprit.entities.OrderItem;
import tn.esprit.entities.Orders;
import tn.esprit.services.DishService;

@Service
public class DishServiceImpl implements DishService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private DishRepository dishRepository;

	@Override
	public List<Dish> findAll() {
		List<Dish> listDish = (List<Dish>) dishRepository.findAll();
		return listDish;
	}

	@Override
	public Dish save(Dish dish) {
		return dishRepository.save(dish);
	}

	@Override
	public Dish findOne(Long id) {
		return dishRepository.findOne(id);
	}

	@Override
	public void removeOne(Long id) {
		dishRepository.delete(id);
		
	}

	@Override
	public List<Dish> customList(Long id) {
		Query q = em.createQuery("SELECT d FROM Dish d, OrderItem oi, Orders o WHERE d.id=oi.dish AND oi.orders=o.id AND o.user="+id+" AND o.rating>=3 GROUP BY oi.dish HAVING (COUNT(oi.dish)>=1) ORDER BY COUNT(oi.dish) DESC");
	    List<Dish> customL = q.getResultList();
	    Query qs = em.createQuery("SELECT d FROM Dish d WHERE d not in (SELECT d FROM Dish d, OrderItem oi, Orders o WHERE d.id=oi.dish AND oi.orders=o.id AND o.user="+id+" GROUP BY oi.dish)");
	    List<Dish> customLs = qs.getResultList();
	    customL.addAll(customLs);
	    return customL;
	}

	@Override
	public List<Dish> statData() {
		Query q = em.createNativeQuery("SELECT COUNT(o.dish_id) FROM restaurantdb.order_item o JOIN restaurantdb.dish d ON o.dish_id=d.id JOIN restaurantdb.user_order u ON o.order_id=u.id WHERE u.order_date between DATE_ADD(NOW(),INTERVAL -1 week) and NOW() GROUP BY o.dish_id ORDER BY COUNT(o.dish_id) DESC");
	    List<Dish> statList = q.getResultList();
	    return statList;
	}

	@Override
	public List<Dish> statLabel() {
		Query q = em.createNativeQuery("SELECT d.title FROM restaurantdb.order_item o JOIN restaurantdb.dish d ON o.dish_id=d.id JOIN restaurantdb.user_order u ON o.order_id=u.id WHERE u.order_date between DATE_ADD(NOW(),INTERVAL -1 week) and NOW() GROUP BY o.dish_id ORDER BY COUNT(o.dish_id) DESC");
	    List<Dish> statList = q.getResultList();
	    return statList;
	}

}
