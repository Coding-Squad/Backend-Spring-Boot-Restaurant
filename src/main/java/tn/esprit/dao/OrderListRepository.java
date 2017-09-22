package tn.esprit.dao;

import org.springframework.data.repository.CrudRepository;

import tn.esprit.entities.OrderList;

public interface OrderListRepository extends CrudRepository<OrderList, Long> {

}
