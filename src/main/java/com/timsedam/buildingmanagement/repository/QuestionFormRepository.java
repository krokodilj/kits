package com.timsedam.buildingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timsedam.buildingmanagement.model.QuestionForm;

public interface QuestionFormRepository extends JpaRepository<QuestionForm, Long> {

}
