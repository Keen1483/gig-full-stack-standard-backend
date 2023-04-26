package com.devskills.gigfullstackstandard.model;

import static jakarta.persistence.GenerationType.AUTO;
import static jakarta.persistence.FetchType.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy=AUTO)
	private Long id;
	
	@Column(unique=true, nullable=false)
	private String email;
	
	@Column(unique=true, nullable=false)
	private String username;
	
	@Column(nullable=false)
	private String password;
	
	@ManyToMany(fetch=EAGER)
	private Collection<Role> roles = new HashSet<>();
	
	@Column(nullable=false)
	private LocalDateTime signupAt;
	
	private LocalDateTime updatedAt;

}
