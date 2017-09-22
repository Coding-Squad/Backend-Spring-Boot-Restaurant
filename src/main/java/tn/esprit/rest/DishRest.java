package tn.esprit.rest;

import java.io.BufferedOutputStream;


import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import tn.esprit.entities.Dish;
import tn.esprit.entities.User;
import tn.esprit.services.DishService;
import tn.esprit.services.UserService;

@RestController
@RequestMapping("/dish")
public class DishRest {
	
	@Autowired
	private DishService dishService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public Dish addDishPost(@RequestBody Dish dish){
		return dishService.save(dish);
		
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public Dish editDish(@RequestBody Dish dish){
		return dishService.save(dish);
	}
	
	@RequestMapping("/dishList")
	public List<Dish> getDishList(){
		return dishService.findAll();
	}
	
	@RequestMapping("/customList")
	public List<Dish> getCustomList(Principal principal){
		User user = userService.findByUsername(principal.getName());
		return dishService.customList(user.getId());
	}
	
	@RequestMapping("/statData")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<Dish> getStatData(){
		return dishService.statData();
	}
	
	@RequestMapping("/statLabel")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<Dish> getStatLabel(){
		return dishService.statLabel();
	}
	
	@RequestMapping("/{id}")
	public Dish getDish(@PathVariable("id") Long id){
		Dish dish = dishService.findOne(id);
		return dish;
	}
	
	@RequestMapping(value="/remove", method=RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity removeDish(@RequestBody String id){
		dishService.removeOne(Long.parseLong(id));
		return new ResponseEntity("Remove success", HttpStatus.OK);
	}
	
	@RequestMapping(value="/add/image", method=RequestMethod.POST)
	public ResponseEntity upload(@RequestParam("id") Long id, 
			                      HttpServletResponse response ,
			                      HttpServletRequest request){
		try {
			Dish dish = dishService.findOne(id);
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Iterator<String> it = multipartRequest.getFileNames();
			MultipartFile multipartFile = multipartRequest.getFile(it.next());
			String fileName = id+".png";
			
			
			byte[] bytes = multipartFile.getBytes();
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/image/dish/"+fileName)));
			stream.write(bytes);
			stream.close();
			
			return new ResponseEntity("Upload Success!", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("Upload failed!", HttpStatus.BAD_REQUEST);
		}
	
	

}
}
