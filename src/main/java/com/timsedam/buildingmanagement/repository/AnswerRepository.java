package com.timsedam.buildingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timsedam.buildingmanagement.model.Answer;
import com.timsedam.buildingmanagement.model.OfferedAnswer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

	Answer save(OfferedAnswer answer);

}
