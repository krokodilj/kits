package com.timsedam.buildingmanagement.service;

import static org.assertj.core.api.Assertions.assertThat;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.timsedam.buildingmanagement.exceptions.UserExistsException;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AdminServiceTest {
	
	@Autowired
    private AdminService adminService;

	@Autowired
	private UserRepository userRepository;
	
	@Test
    @Transactional
    @Rollback(true)
    public void testCreate() throws UserExistsException {
    	User admin = new User();
    	admin.setUsername("username");
    	admin.setPassword("password");
    	admin.setEmail("email@gmail.com");
    	
    	long numUsersBeforeCreate = userRepository.count();
    	
    	User result = adminService.create(admin);
    	assertThat(result).isNotNull();
    	
    	assertThat(userRepository.count()).isEqualTo(numUsersBeforeCreate + 1);
    	User adminFromDB = userRepository.findOne(result.getId());
    	assertThat(adminFromDB.getUsername()).isEqualTo(admin.getUsername());
    	assertThat(adminFromDB.getPassword()).isEqualTo(admin.getPassword());
    	assertThat(adminFromDB.getEmail()).isEqualTo(admin.getEmail());
    }
	
	@Test(expected = UserExistsException.class)
	@Transactional
	@Rollback(true)
	public void testUsernameNotUnique() throws UserExistsException {
		User admin = new User();
    	admin.setUsername("admin1");
    	admin.setPassword("admin1");
    	admin.setEmail("email@gmail.com");
    	
    	adminService.create(admin);
	}
    
}
