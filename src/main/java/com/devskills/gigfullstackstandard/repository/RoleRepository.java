package com.devskills.gigfullstackstandard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devskills.gigfullstackstandard.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(String name);
	void deleteByName(String name);
}
