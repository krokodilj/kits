package com.timsedam.buildingmanagement.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.timsedam.buildingmanagement.dto.response.ForwardDTO;
import com.timsedam.buildingmanagement.dto.response.UserDTO;
import com.timsedam.buildingmanagement.model.Forward;

@Component
public class ForwardMapper {

	@Autowired
	UserMapper userMapper;
	
	public ForwardDTO toDTO(Forward currentHolder) {
		UserDTO forwarder = null;
		if(currentHolder.getForwarder()!=null){
			forwarder = userMapper.toDto(currentHolder.getForwarder());
		}
		
		ForwardDTO fDTO = new ForwardDTO(currentHolder.getId(), 
				forwarder,
				userMapper.toDto(currentHolder.getForwardedTo()));
		return fDTO;
	}

}
