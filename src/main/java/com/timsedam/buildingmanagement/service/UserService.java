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

import com.timsedam.buildingmanagement.model.Permission;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	public void save(User user) {
		userRepository.save(user);
	}
	
	public boolean exists(String username) {
		return userRepository.existsByUsername(username);
	}
	
	@Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null)
            throw new UsernameNotFoundException(String.format("No user found with username %s", username));
        else {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
            for(Permission p : user.getRole().getPermissions()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(p.getName()));
            }
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                    grantedAuthorities);
        }
    }
	
}
