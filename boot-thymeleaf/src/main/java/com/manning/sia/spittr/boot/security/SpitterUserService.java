package com.manning.sia.spittr.boot.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.manning.sia.spittr.boot.data.SpitterRepository;
import com.manning.sia.spittr.boot.domain.Spitter;


public class SpitterUserService implements UserDetailsService {
	private SpitterRepository spitterRepository;

	// SpitterRepository will be injected
	public SpitterUserService(SpitterRepository spitterRepo) {
		spitterRepository = spitterRepo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Spitter spitter = spitterRepository.findByUsername(username);
		if (spitter != null) {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("ROLE_SPITTER"));
			
			return new User(spitter.getUsername(), spitter.getPassword(), authorities);
		}
		throw new UsernameNotFoundException("User '" + username + "' not found.");
	}

}
