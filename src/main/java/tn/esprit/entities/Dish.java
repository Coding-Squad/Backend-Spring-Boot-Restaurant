package tn.esprit.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

@Entity
@NamedQueries({
    @NamedQuery(name=Dish.QUERY_FIND_DISH,query="SELECT COUNT(d.id) FROM Dish d")
})
public class Dish implements Serializable{
	
	public static final String QUERY_FIND_DISH = "LoginFindUser";
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String title;
	private String description;
	private float price;
	
	@Transient
	private MultipartFile dishImage;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public MultipartFile getDishImage() {
		return dishImage;
	}

	public void setDishImage(MultipartFile dishImage) {
		this.dishImage = dishImage;
	}
	

}
