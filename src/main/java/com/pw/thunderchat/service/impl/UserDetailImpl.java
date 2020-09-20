package com.pw.thunderchat.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pw.thunderchat.model.User;
import com.pw.thunderchat.repository.UserRepository;

/**
 * @author André
 *
 */
@Service
public class UserDetailImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * Load by user para autenticação JWT
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findUserByName(username);
		System.out.println(user + "entrei aqui");
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return userDetailWithRole(user.getMention(), user.getPassword());
	}

	public UserDetails userDetailWithRole(String mention, String password) {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		list.add(new SimpleGrantedAuthority("ROLE_USER"));
		return new org.springframework.security.core.userdetails.User(mention, password, list);
	}

}