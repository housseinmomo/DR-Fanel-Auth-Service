package com.nethealth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nethealth.dtos.LoginDto;
import com.nethealth.utils.LoginServiceUtils;

@Service
public class LoginService implements UserDetailsService{
	
	@Autowired
	LoginServiceUtils userDetailServiceUtils;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		LoginDto loginModel = userDetailServiceUtils.createUserDetail(username);
		if(loginModel == null) {
			throw new UsernameNotFoundException("user doesn't found");
		}
		List<GrantedAuthority> grantedAuthority = userDetailServiceUtils.addAuthority(username);
		return new User(loginModel.getUsername(), loginModel.getPassword(), grantedAuthority);
	}
	
	

}
