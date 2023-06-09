package com.devskills.gigfullstackstandard.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.devskills.gigfullstackstandard.exception.BadContentException;
import com.devskills.gigfullstackstandard.exception.ResourceNotFoundException;
import com.devskills.gigfullstackstandard.model.Role;
import com.devskills.gigfullstackstandard.repository.RoleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepo;

	@Override
	public List<Role> getRoles() {
		log.info("Fetching all roles");
		return roleRepo.findAll();
	}

	@Override
	public Role getRole(String name) {
		log.info("Fetching role {}", name);
		Optional<Role> optionalRole = roleRepo.findByName(name);
		
		if (optionalRole.isPresent()) {
			return optionalRole.get();
		} else {
			log.error("Role {} not found in database", name);
			throw new ResourceNotFoundException("Role " + name + " not found in database");
		}
	}

	@Override
	public Role saveRole(Role role) {
		if (role == null || role.getName() == null) {
			log.error("Cannot create empty role");
			throw new BadContentException("Cannot create empty role");
		} else {
			role.setName(role.getName().toUpperCase());
			
			if (role.getName().matches("^((?!ROLE)(?!ROLE_)[A-Z0-9_])+$")) {
				log.info("Saving new role {} in the database", role.getName());
				role.setName("ROLE_" + role.getName());
				return roleRepo.save(role);
			} else {
				log.error("Role {} is in a bad format", role.getName());
				throw new BadContentException("Role " + role.getName() + " is in a bad format");
			}
		}
	}

	@Override
	public Role updateRole(Long id, Role role) {
		Optional<Role> optionalRole = roleRepo.findById(id);
		
		if (optionalRole.isPresent()) {
			Role currentRole = optionalRole.get();
			log.info("Updating role: {}", currentRole.getName());
			
			String name = role.getName();
			if (name != null) {
				currentRole.setName(name);
			}
			return roleRepo.save(currentRole);
		} else {
			log.error("Role {} not found in the database", role.toString());
			throw new ResourceNotFoundException("Role " + role.toString() + " not found in the database");
		}
	}

	@Override
	public void deleteRole(String name) {
		Optional<Role> optionalRole = roleRepo.findByName(name);
		
		if (optionalRole.isPresent()) {
			log.warn("Deleting role {} in the database", name);
			roleRepo.deleteByName(name);
		} else {
			log.error("Role {} not found in the database", name);
			throw new ResourceNotFoundException("Role " + name + " not found in the database");
		}

	}

}
