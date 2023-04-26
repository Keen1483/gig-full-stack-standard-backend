package com.devskills.gigfullstackstandard.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devskills.gigfullstackstandard.exception.BadContentException;
import com.devskills.gigfullstackstandard.exception.ResourceNotFoundException;
import com.devskills.gigfullstackstandard.model.Role;
import com.devskills.gigfullstackstandard.model.User;
import com.devskills.gigfullstackstandard.repository.RoleRepository;
import com.devskills.gigfullstackstandard.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository userRepo;
	private final RoleRepository roleRepo;
	private final PasswordEncoder passwordEncoder;

	@Override
	public List<User> getUsers() {
		log.info("Fetching all users");
		return userRepo.findAll();
	}

	@Override
	public User getUser(String username) {
		log.info("Fetching user {}", username);
		Optional<User> optionalUser = userRepo.findByUsername(username);
		
		if (optionalUser.isPresent()) {
			return optionalUser.get();
		} else {
			log.error("User {} not found in the database", username);
			throw new ResourceNotFoundException("User " + username + " not found in the database");
		}
	}

	@Override
	public User saveUser(User user) {
		if (user.getEmail() == null || user.getPassword() == null) {
			throw new BadContentException("Cannot create empty user: " + user.toString());
		} else {
			log.info("Saving new user {} in the database", user.getUsername());
			String username = user.getUsername() == null ? user.getEmail() : user.getUsername();
			user.setUsername(username);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setSignupAt(LocalDateTime.now());
			return userRepo.save(user);
		}
	}

	@Override
	public User updateUser(Long id, User user) {
		Optional<User> optionalUser = userRepo.findById(id);
		
		if (optionalUser.isPresent()) {
			User currentUser = optionalUser.get();
			log.info("Updating user: {}", currentUser.getUsername());
			
			String email = user.getEmail();
			if (email != null && !email.equals(currentUser.getEmail())) {
				currentUser.setEmail(email);
			}
			
			String username = user.getUsername();
			if (username != null && !username.equals(currentUser.getUsername())) {
				currentUser.setUsername(username);
			}
			
			String password = user.getPassword();
			if (password != null && !passwordEncoder.matches(password, currentUser.getPassword())) {
				currentUser.setPassword(passwordEncoder.encode(password));
			}
			
			currentUser.setUpdatedAt(LocalDateTime.now());
			
			// Save updated data before adding new roles because saving user can be failed
			User updatedUser = userRepo.save(currentUser);
			
			// If user is successfully updated, we can add new roles if they exist
			Collection<Role> roles = user.getRoles();
			roles.removeAll(updatedUser.getRoles());
			if (!roles.isEmpty()) {
				return this.addRolesToUser(updatedUser.getUsername(), roles);
			}
			
			return updatedUser;
		} else {
			log.error("User {} not found in database", user.getUsername());
			throw new ResourceNotFoundException("User " + user.getUsername() + " not found in database");
		}
	}

	@Override
	public void deleteUser(String username) {
		Optional<User> optionalUser = userRepo.findByUsername(username);
		
		if (optionalUser.isPresent()) {
			log.warn("Deleting user {} in the database", username);
			userRepo.deleteByUsername(username);
		} else {
			log.error("User {} not found in the database", username);
			throw new ResourceNotFoundException("User " + username + " not found in the database");
		}
	}

	@Override
	public User addRolesToUser(String username, Collection<Role> roles) {
		Optional<User> optionalUser = userRepo.findByUsername(username);
		
		if (optionalUser.isPresent() && !roles.isEmpty()) {
			User user = optionalUser.get();
			
			for (Role role : roles) {
				if (role != null && roleRepo.findByName(role.getName()).isPresent()) {
					log.info("Adding role {} to user {}", role.getName(), username);
					// Here, role ID can be null and role name can already exist in the database
					user.getRoles().add(roleRepo.findByName(role.getName()).get());
				} else {
					log.warn("Role {} not found in the database", role);
				}
			}
			return saveUser(user);
		} else {
			log.error("User {} not found in the database or roles collection {} is empty", username, roles);
			throw new ResourceNotFoundException("User " + username + " not found in the database or roles collection " + roles.toString() + " is empty");
		}
	}

}
