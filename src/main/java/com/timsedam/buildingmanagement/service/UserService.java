package com.timsedam.buildingmanagement.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.timsedam.buildingmanagement.exceptions.UserMissingException;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.Permission;
import com.timsedam.buildingmanagement.model.Role;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	public User findOne(Long id) throws UserMissingException {
		User user = userRepository.findOne(id);
		if(user == null)
			throw new UserMissingException(id);
		else
			return user;
	}
	
	public User findOneByUsername(String username) throws UserMissingException {
		User user = userRepository.findByUsername(username);
		if(user == null)
			throw new UserMissingException(username);
		else 
			return user;
	}
	
	@Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null)
            throw new UsernameNotFoundException(String.format("No user found with username %s", username));
        else {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
            for(Role role : user.getRoles()) {
            	for(Permission permission : role.getPermissions()) {
                    grantedAuthorities.add(new SimpleGrantedAuthority(permission.getName()));
                }
            }
            return new org.springframework.security.core.userdetails.User(
            		user.getUsername(), user.getPassword(), grantedAuthorities);
        }
    }

	
}
