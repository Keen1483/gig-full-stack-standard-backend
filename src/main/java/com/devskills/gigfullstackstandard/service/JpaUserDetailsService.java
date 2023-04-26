package com.devskills.gigfullstackstandard.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devskills.gigfullstackstandard.model.SecurityUser;
import com.devskills.gigfullstackstandard.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

	private final UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo
				.findByUsername(username)
				.map(SecurityUser::new)
				.orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found in the database"));
	}

}
