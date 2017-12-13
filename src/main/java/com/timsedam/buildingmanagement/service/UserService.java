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

import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.Permission;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.repository.BuildingRepository;
import com.timsedam.buildingmanagement.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BuildingRepository buildingRepository;
	
	public void save(User user) {
		userRepository.save(user);
	}
	
	public User findOneByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public boolean exists(String username) {
		return userRepository.existsByUsername(username);
	}
	
	public boolean isManager(Long userId, Long buildingId) {
		Building building = buildingRepository.findOne(buildingId);
		if(building.getManager().getId() == userId)
			return true;
		else
			return false;
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
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                    grantedAuthorities);
        }
    }
	
}
