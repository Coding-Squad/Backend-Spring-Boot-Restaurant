package tn.esprit.entities;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class OrderItem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private int qty;
	private BigDecimal subTotal;
	
	@OneToOne
	@JoinColumn(name="dish_id")
	private Dish dish;
	
	@OneToMany(mappedBy="orderItem")
	@JsonIgnore
	private List<DishToOrderItem> dishToOrderItemList;
	
	
	@ManyToOne
	@JoinColumn(name="order_list_id")
	@JsonIgnore
	private OrderList orderList;
	
	@ManyToOne
	@JoinColumn(name="order_id")
	@JsonIgnore
	private Orders orders;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public int getQty() {
		return qty;
	}


	public void setQty(int qty) {
		this.qty = qty;
	}


	public BigDecimal getSubTotal() {
		return subTotal;
	}


	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}


	public Dish getDish() {
		return dish;
	}


	public void setDish(Dish dish) {
		this.dish = dish;
	}


	public List<DishToOrderItem> getDishToOrderItemList() {
		return dishToOrderItemList;
	}


	public void setDishToOrderItemList(List<DishToOrderItem> dishToOrderItemList) {
		this.dishToOrderItemList = dishToOrderItemList;
	}


	public OrderList getOrderList() {
		return orderList;
	}


	public void setOrderList(OrderList orderList) {
		this.orderList = orderList;
	}


	public Orders getOrders() {
		return orders;
	}


	public void setOrders(Orders orders) {
		this.orders = orders;
	}
	
	

	
}
