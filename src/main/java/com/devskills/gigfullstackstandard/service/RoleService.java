package com.devskills.gigfullstackstandard.service;

import java.util.List;

import com.devskills.gigfullstackstandard.model.Role;

public interface RoleService {
	List<Role> getRoles();
	Role getRole(String name);
	Role saveRole(Role role);
	Role updateRole(Long id, Role role);
	void deleteRole(String name);
}
