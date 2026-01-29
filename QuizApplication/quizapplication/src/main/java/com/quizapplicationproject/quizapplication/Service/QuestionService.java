package com.quizapplicationproject.quizapplication.Service;


import com.quizapplicationproject.quizapplication.DAO.QuestionDao;
import com.quizapplicationproject.quizapplication.Entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionDao questionDao;


    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return ResponseEntity.ok(questionDao.findByCategory(category));
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
      try {
          questionDao.save(question);
          return new ResponseEntity<>("Success",HttpStatus.CREATED);
      } catch (Exception e) {
          e.printStackTrace();
      }
      return  new ResponseEntity<>(" ",HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteQuestion(Long id) {
        try {
            questionDao.deleteById(id);
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.NO_CONTENT);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(" ",HttpStatus.BAD_REQUEST);
    }
}
