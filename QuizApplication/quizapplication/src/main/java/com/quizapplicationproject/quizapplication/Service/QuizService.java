package com.quizapplicationproject.quizapplication.Service;

import com.quizapplicationproject.quizapplication.DAO.QuestionDao;
import com.quizapplicationproject.quizapplication.DAO.QuizDao;
import com.quizapplicationproject.quizapplication.Entity.Question;
import com.quizapplicationproject.quizapplication.Entity.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizDao quizDao;
    @Autowired
    private QuestionDao questionDao;


    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        try{
            List<Question> questions = questionDao.findRandomQuestionByCategory(category,numQ);
            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestions(questions);
            quizDao.save(quiz);
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(" ",HttpStatus.BAD_REQUEST);
    }
}
