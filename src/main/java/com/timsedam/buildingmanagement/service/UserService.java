package com.timsedam.buildingmanagement.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.timsedam.buildingmanagement.exceptions.RoleInvalidException;
import com.timsedam.buildingmanagement.exceptions.UserExistsException;
import com.timsedam.buildingmanagement.exceptions.UserMissingException;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.Permission;
import com.timsedam.buildingmanagement.model.Residence;
import com.timsedam.buildingmanagement.model.Role;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.repository.BuildingRepository;
import com.timsedam.buildingmanagement.repository.RoleRepository;
import com.timsedam.buildingmanagement.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BuildingRepository buildingRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	public boolean exists(String username) {
		return userRepository.existsByUsername(username);
	}
	
	public User findOne(Long id) throws UserMissingException {
		User user = userRepository.findOne(id);
		if(user == null)
			throw new UserMissingException(id);
		else
			return user;
	}
	
	public User save(User user, String userRole) throws UserExistsException, RoleInvalidException {
		if(exists(user.getUsername()))
			throw new UserExistsException(user.getUsername());
		
		List<String> validRoles = Arrays.asList("ADMIN", "RESIDENT", "MANAGER", "COMPANY");
		if(!validRoles.contains(userRole))
			throw new RoleInvalidException(userRole);
		
		Role role = roleRepository.findOneByName(userRole);
		user.getRoles().add(role);
		return userRepository.save(user);
	}
	
	public User findOneByUsername(String username) throws UserMissingException {
		User user = userRepository.findByUsername(username);
		if(user == null)
			throw new UserMissingException(username);
		else 
			return user;
	}
	
	public boolean isManager(Long userId, Long buildingId) {
		Building building = buildingRepository.findOne(buildingId);
		if(building.getManager().getId() == userId)
			return true;
		else
			return false;
	}
	
	public boolean isResident(Long userId, Long buildingId) {
		Building building = buildingRepository.findOne(buildingId);
		for(Residence residence : building.getResidences()) {
			for(User resident : residence.getResidents()) {
				if(resident.getId() == userId)
					return true;
			}
		}
		return false;
	}
	
	public boolean isApartmentOwnerInBuilding(Long userId, Long buildingId) {
		Building building = buildingRepository.findOne(buildingId);
		for(Residence residence : building.getResidences()) {
			if(userId == residence.getApartmentOwner().getId())
				return true;
		}
		return false;
	}
		
	public boolean isResidentOrApartmentOwnerInBuilding(Long userId, Long buildingId) {
		Building building = buildingRepository.findOne(buildingId);
		for(Residence residence : building.getResidences()) {
			for(User resident : residence.getResidents()) {
				if(userId == resident.getId()) {
					return true;
				}
			}
			if(userId == residence.getApartmentOwner().getId())
				return true;
		}
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
