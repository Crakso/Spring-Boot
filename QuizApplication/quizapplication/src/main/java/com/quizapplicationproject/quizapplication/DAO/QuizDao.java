package com.quizapplicationproject.quizapplication.DAO;

import com.quizapplicationproject.quizapplication.Entity.Question;
import com.quizapplicationproject.quizapplication.Entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface QuizDao extends JpaRepository<Quiz, Long> {


}