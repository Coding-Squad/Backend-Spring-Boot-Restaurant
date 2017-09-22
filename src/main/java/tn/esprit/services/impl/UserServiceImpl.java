package tn.esprit.services.impl;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.dao.RoleRepository;
import tn.esprit.dao.UserRepository;
import tn.esprit.entities.OrderItem;
import tn.esprit.entities.OrderList;
import tn.esprit.entities.Rating;
import tn.esprit.entities.User;
import tn.esprit.entities.UserRole;
import tn.esprit.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Transactional
	public User createUser(User user, Set<UserRole> userRoles){
		User localUser = userRepository.findByUsername(user.getUsername());
	if (localUser != null){
		LOG.info("User with username {} already exists", user.getUsername());
	} else {
		for (UserRole ur : userRoles){
			roleRepository.save(ur.getRole());
		}
		user.getUserRoles().addAll(userRoles);
		
		OrderList orderList = new OrderList();
		orderList.setUser(user);
		user.setOrderList(orderList);
		
		Rating rating = new Rating();
		rating.setUser(user);
		user.setRating(rating);
		
		localUser = userRepository.save(user);
	}
	return localUser;
	}
	
	@Override
	public User save(User user){
		return userRepository.save(user);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public List<User> findAll() {
		List<User> listUser = (List<User>) userRepository.findAll();
		return listUser;
	}

	@Override
	public User findOne(Long id) {	
		return userRepository.findOne(id);
	}

}
