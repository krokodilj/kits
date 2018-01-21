package com.timsedam.buildingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timsedam.buildingmanagement.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{

}
