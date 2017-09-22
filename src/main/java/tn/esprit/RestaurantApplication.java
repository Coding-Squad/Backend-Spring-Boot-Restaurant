package tn.esprit;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import tn.esprit.config.SecurityUtility;
import tn.esprit.entities.Role;
import tn.esprit.entities.User;
import tn.esprit.entities.UserRole;
import tn.esprit.services.UserSecurityService;
import tn.esprit.services.UserService;

@SpringBootApplication
@EntityScan("tn.esprit.entities")
public class RestaurantApplication  implements CommandLineRunner{


	public static void main(String[] args) {
		SpringApplication.run(RestaurantApplication.class, args);
	}
	
	@Autowired
	private UserService userService;
	
	@Override
	public void run(String... args) throws Exception {
		
		User user1 = new User();
		user1.setFirstName("Amine");
		user1.setLastName("Amine");
		user1.setUsername("amine");
		user1.setPassword(SecurityUtility.passwordEncoder().encode("amine"));
		Set<UserRole> userRoles = new HashSet<>();
		Role role1 = new Role();
		role1.setRoleID(1);
		role1.setName("ROLE_USER");
		userRoles.add(new UserRole(user1, role1));
		
		userService.createUser(user1, userRoles);
		
		userRoles.clear();
		
		User user3 = new User();
		user3.setFirstName("haouala");
		user3.setLastName("haouala");
		user3.setUsername("haouala");
		user3.setPassword(SecurityUtility.passwordEncoder().encode("haouala"));
		userRoles.add(new UserRole(user3, role1));
		
		userService.createUser(user3, userRoles);
		
		userRoles.clear();
		
		User user2 = new User();
		user2.setFirstName("Admin");
		user2.setLastName("Admin");
		user2.setUsername("admin");
		user2.setPassword(SecurityUtility.passwordEncoder().encode("admin"));
		Role role2 = new Role();
		role2.setRoleID(0);
		role2.setName("ROLE_ADMIN");
		userRoles.add(new UserRole(user2, role2));
		
		userService.createUser(user2, userRoles);
		
		
		
		
	}
}
