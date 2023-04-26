package com.devskills.gigfullstackstandard.service;

import java.util.Collection;
import java.util.List;

import com.devskills.gigfullstackstandard.model.Role;
import com.devskills.gigfullstackstandard.model.User;

public interface UserService {
	List<User> getUsers();
	User getUser(String username);
	User saveUser(User user);
	User updateUser(Long id, User user);
	void deleteUser(String username);
	User addRolesToUser(String username, Collection<Role> roles);
}
