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
import com.timsedam.buildingmanagement.model.Company;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CompanyServiceTest {
	
	@Autowired
    private CompanyService companyService;

	@Autowired
	private UserRepository userRepository;
	
	@Test
    @Transactional
    @Rollback(true)
    public void testCreate() throws UserExistsException {
    	Company company = new Company();
    	company.setUsername("username");
    	company.setPassword("password");
    	company.setEmail("email@gmail.com");
    	company.setPhoneNumber("12345");
    	company.setPIB("PIB");
    	
    	long numUsersBeforeCreate = userRepository.count();
    	
    	User result = companyService.create(company);
    	assertThat(result).isNotNull();
    	
    	assertThat(userRepository.count()).isEqualTo(numUsersBeforeCreate + 1);
    	Company companyFromDB = (Company)userRepository.findOne(result.getId());
    	assertThat(companyFromDB.getUsername()).isEqualTo(company.getUsername());
    	assertThat(companyFromDB.getPassword()).isEqualTo(company.getPassword());
    	assertThat(companyFromDB.getEmail()).isEqualTo(company.getEmail());
    	assertThat(companyFromDB.getPhoneNumber()).isEqualTo(company.getPhoneNumber());
    	assertThat(companyFromDB.getPIB()).isEqualTo(company.getPIB());
    }
	
	@Test(expected = UserExistsException.class)
	@Transactional
	public void testCreateUsernameNotUnique() throws UserExistsException {
		Company company = new Company();
    	company.setUsername("company1");
    	company.setPassword("password");
    	company.setEmail("email@gmail.com");
    	company.setPhoneNumber("12345");
    	company.setPIB("PIB");
    	
    	companyService.create(company);
	}
    
}