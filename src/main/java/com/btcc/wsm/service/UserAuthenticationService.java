package com.btcc.wsm.service;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserAuthenticationService {
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException;
}
