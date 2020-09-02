package com.redhat.bluesmile.sprintsapp.service;

import static java.util.Collections.emptyList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.redhat.bluesmile.sprintsapp.model.UserModel;
import com.redhat.bluesmile.sprintsapp.repository.SprintsAppRepository;
import com.redhat.bluesmile.sprintsapp.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

//	private ApplicationUserRepository applicationUserRepository;
////
////	public UserDetailsServiceImpl(ApplicationUserRepository applicationUserRepository) {
////		this.applicationUserRepository = applicationUserRepository;
////	}
//
////	@Override
////	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
////		UserModel userModel = applicationUserRepository.findByEmail(email);
////		if (userModel == null) {
////			throw new UsernameNotFoundException(email);
////		}
////		return new User(userModel.getEmail(), userModel.getPassword(), emptyList());
////	}
//
	private UserRepository userRepository;

	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserModel userModel = userRepository.findByEmail(email);
		if (userModel == null) {
			throw new UsernameNotFoundException(email);
		}
		return new User(userModel.getEmail(), userModel.getPassword(), emptyList());
	}
}
