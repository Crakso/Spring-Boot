package com.quizapplicationproject.quizapplication.DAO;

import com.quizapplicationproject.quizapplication.Entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question,Long> {

    List<Question> findByCategory(String category);


    @Query(value = "SELECT * FROM question q WHERE q.category=:category ORDER BY RAND() LIMIT :numQ",nativeQuery = true)
    List<Question> findRandomQuestionByCategory(@RequestParam  String category,@RequestParam int numQ);
}
