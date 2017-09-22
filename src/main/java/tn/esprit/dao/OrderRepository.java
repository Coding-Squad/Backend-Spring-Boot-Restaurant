package tn.esprit.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import tn.esprit.entities.Orders;
import tn.esprit.entities.User;

public interface OrderRepository extends CrudRepository<Orders, Long>{
	
List<Orders> findByUser(User user);
}
