package com.devskills.gigfullstackstandard.controller;

import java.net.URI;
import java.util.Collection;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devskills.gigfullstackstandard.model.Role;
import com.devskills.gigfullstackstandard.model.User;
import com.devskills.gigfullstackstandard.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	
	// GET METHODS
	@GetMapping("")
	public ResponseEntity< List<User> > getUsers() {
		return ResponseEntity.ok().body(userService.getUsers());
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<User> getUser(@PathVariable("username") String username) {
		return ResponseEntity.ok().body(userService.getUser(username));
	}
	
	@GetMapping("/signin")
	public ResponseEntity<User> signin() {
		return ResponseEntity.ok().body(new User());
	}
	
	// POST METHODS
	@PostMapping("/save")
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/save").toUriString());
		return ResponseEntity.created(uri).body(userService.saveUser(user));
	}
	
	// PUT METHODS
	@PutMapping("/edit/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
		return ResponseEntity.ok().body(userService.updateUser(id, user));
	}
	
	@PutMapping("/addroles/{username}")
	public ResponseEntity<User> addRolesToUser(@PathVariable("username") String username, @RequestBody Collection<Role> roles) {
		return ResponseEntity.ok().body(userService.addRolesToUser(username, roles));
	}
	
	// DELETE METHODS
	@DeleteMapping("/delete/{username}")
	public ResponseEntity<?> deleteUser(@PathVariable("username") String username) {
		userService.deleteUser(username);
		return ResponseEntity.ok().build();
	}
}
