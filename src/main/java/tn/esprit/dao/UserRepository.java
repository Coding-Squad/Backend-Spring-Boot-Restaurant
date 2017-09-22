package tn.esprit.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import tn.esprit.entities.User;

public interface UserRepository extends CrudRepository<User, Long>{
	
	User findByUsername(String username);

}
