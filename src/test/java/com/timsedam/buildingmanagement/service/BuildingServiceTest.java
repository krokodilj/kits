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

import com.timsedam.buildingmanagement.exceptions.BuildingExistsException;
import com.timsedam.buildingmanagement.exceptions.BuildingMissingException;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.repository.BuildingRepository;
import com.timsedam.buildingmanagement.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BuildingServiceTest {
	
	@Autowired
	private BuildingService buildingService;
	
	@Autowired
	private BuildingRepository buildingRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
    @Transactional
    public void testFindOne() throws BuildingMissingException, BuildingMissingException {
		Building result = buildingService.findOne(1L);
		assertThat(result).isNotNull();
    }
	
	@Test(expected = BuildingMissingException.class)
    @Transactional
	public void testFindOneBuildingMissing() throws BuildingMissingException {
		buildingService.findOne(55L);
	}
	
	@Test
    @Transactional
    @Rollback(true)
    public void testCreate() throws BuildingExistsException {
		Building building = new Building();
		building.setCity("CITYY");
		building.setAddress("ADDRESS");
		building.setCountry("COUNTRY");
		building.setApartmentCount(1);
		building.setDescription("DESCRIPTION");
		
    	long numBuildingsBeforeCreate = buildingRepository.count();
    	
    	Building result = buildingService.create(building);
    	assertThat(result).isNotNull();
    	
    	assertThat(buildingRepository.count()).isEqualTo(numBuildingsBeforeCreate + 1);
    	Building buildingFromDB = buildingRepository.findOne(result.getId());
    	assertThat(building.getCity()).isEqualTo(buildingFromDB.getCity());
    	assertThat(building.getAddress()).isEqualTo(buildingFromDB.getAddress());
    	assertThat(building.getCountry()).isEqualTo(buildingFromDB.getCountry());
    	assertThat(building.getApartmentCount()).isEqualTo(buildingFromDB.getApartmentCount());
    	assertThat(building.getDescription()).isEqualTo(buildingFromDB.getDescription());
    }
	
	@Test(expected = BuildingExistsException.class)
    @Transactional
    @Rollback(true)
    public void testCreateBuildingExists() throws BuildingExistsException {
		Building building = new Building();
		building.setCity("CITY");
		building.setAddress("ADDRESS");
		building.setCountry("COUNTRY");
		building.setApartmentCount(1);
		building.setDescription("DESCRIPTION");
    	
    	buildingService.create(building);
    }
	
	@Test
    @Transactional
    public void testIsManagerTrue() {
		Building building = buildingRepository.findOne(1L);
		User user = building.getManager();
		
    	assertThat(buildingService.isManager(building, user)).isTrue();
    }
	
	@Test
    @Transactional
    public void testIsManagerFalse() {
		Building building = buildingRepository.findOne(1L);
		User user = userRepository.findOne(35L);
		
    	assertThat(buildingService.isManager(building, user)).isFalse();
    }
	
	@Test
    @Transactional
    public void testIsResidentOrApartmentOwner() {
		Building building = buildingRepository.findOne(1L);
		User resident = building.getResidences().get(0).getResidents().get(0);
		User apartmentOwner = building.getResidences().get(0).getApartmentOwner();
		
		assertThat(buildingService.isResidentOrApartmentOwner(resident, building)).isTrue();
		assertThat(buildingService.isResidentOrApartmentOwner(apartmentOwner, building)).isTrue();
		
		User notResident = userRepository.findOne(5L);
		User notApartmentOwner = userRepository.findOne(2L);
		
		assertThat(buildingService.isResidentOrApartmentOwner(notResident, building)).isFalse();
		assertThat(buildingService.isResidentOrApartmentOwner(notApartmentOwner, building)).isFalse();
    }
	
	@Test
    @Transactional
    public void testIsApartmentOwner() {
		Building building = buildingRepository.findOne(1L);
		User apartmentOwner = building.getResidences().get(0).getApartmentOwner();
		assertThat(buildingService.isApartmentOwner(apartmentOwner, building)).isTrue();
		
		User notApartmentOwner = userRepository.findOne(2L);
		assertThat(buildingService.isApartmentOwner(notApartmentOwner, building)).isFalse();
    }
	
	@Test
    @Transactional
    public void testIsResident() {
		Building building = buildingRepository.findOne(1L);
		
		User resident = building.getResidences().get(0).getResidents().get(0);
		assertThat(buildingService.isResident(resident, building)).isTrue();
		
		User notResident = userRepository.findOne(5L);
		assertThat(buildingService.isResident(notResident, building)).isFalse();
    }

}
