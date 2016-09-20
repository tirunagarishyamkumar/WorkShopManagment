package com.anny.wsm.serviceimpl;


import com.anny.wsm.model.Users;
import com.anny.wsm.service.UserAuthenticationService;
import com.anny.wsm.service.UsersService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserAuthenticationServiceImpl implements UserAuthenticationService, UserDetailsService{

	UsersService userService;
	

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Users user = null;
		try {
			user = username != null && !username.equals("") ? userService.findByUsername(username) : null;
	        if (user == null || user.getPassword() == null) {
	            throw new UsernameNotFoundException("No such user: " + username);
	        }
		} catch(Exception e) {
			throw new UsernameNotFoundException("No such user: " + username);
		}
        return new UserDetailsImpl(user);
	}
	
	public UsersService getUserService() {
		return userService;
	}
	public void setUserService(UsersService userService) {
		this.userService = userService;
	}
}
