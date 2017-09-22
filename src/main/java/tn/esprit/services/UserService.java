package tn.esprit.services;

import java.util.List;

import java.util.Set;

import tn.esprit.entities.User;
import tn.esprit.entities.UserRole;

public interface UserService {
	
	User createUser(User user, Set<UserRole> userRoles);
    List<User> findAll();
    User findOne(Long id);
	User findByUsername(String username);
	User save(User save);

}
