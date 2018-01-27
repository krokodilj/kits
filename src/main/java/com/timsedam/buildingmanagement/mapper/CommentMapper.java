package com.timsedam.buildingmanagement.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.timsedam.buildingmanagement.dto.response.CommentDTO;
import com.timsedam.buildingmanagement.dto.response.UserDTO;
import com.timsedam.buildingmanagement.model.Comment;
import com.timsedam.buildingmanagement.model.User;

@Component
public class CommentMapper {
	
	@Autowired
    private UserMapper userMapper;
	
	public CommentDTO toDto(Comment comment){
        CommentDTO dto = 
        	new CommentDTO(comment.getId(), comment.getData(), null, comment.getPostedAt());

        User user = comment.getCommenter();
        UserDTO userDTO = userMapper.toDto(user);
        dto.setCommenter(userDTO);
        return dto;
    }
    
    public ArrayList<CommentDTO> toDto(List<Comment> comments) {
    	ArrayList<CommentDTO> result = new ArrayList<CommentDTO>();
    	for(Comment comment : comments) {
    		result.add(toDto(comment));
    	}
    	return result;
    }
}
