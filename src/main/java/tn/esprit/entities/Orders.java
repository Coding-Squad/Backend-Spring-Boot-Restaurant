package tn.esprit.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="user_order")
@NamedQueries({
    @NamedQuery(name=Orders.QUERY_COUNT_ORDER,query="SELECT COUNT(o.id) FROM Orders o WHERE o.orderDate BETWEEN ?1 AND ?2")
})
public class Orders implements Serializable{
	
	public static final String QUERY_COUNT_ORDER = "Login";
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private Date orderDate;
	private BigDecimal ordertotal;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	@JsonIgnore
	private User user;
	
	@OneToMany(mappedBy="orders", cascade=CascadeType.ALL)
	private List<OrderItem> orderItemList;
	
	private int rating;
	
	private String comeBack;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public BigDecimal getOrdertotal() {
		return ordertotal;
	}

	public void setOrdertotal(BigDecimal ordertotal) {
		this.ordertotal = ordertotal;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getComeBack() {
		return comeBack;
	}

	public void setComeBack(String comeBack) {
		this.comeBack = comeBack;
	}
	
	
	
	

}
