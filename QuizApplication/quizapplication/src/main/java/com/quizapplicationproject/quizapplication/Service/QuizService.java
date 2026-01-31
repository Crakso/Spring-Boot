package com.quizapplicationproject.quizapplication.Service;

import com.quizapplicationproject.quizapplication.DAO.QuestionDao;
import com.quizapplicationproject.quizapplication.DAO.QuizDao;
import com.quizapplicationproject.quizapplication.Entity.Question;
import com.quizapplicationproject.quizapplication.Entity.QuestionWrapper;
import com.quizapplicationproject.quizapplication.Entity.Quiz;
import com.quizapplicationproject.quizapplication.Entity.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public ResponseEntity<String> deleteQuizById(Long id) {
        try{
            quizDao.deleteById(id);
            return new ResponseEntity<>("Quiz deleted successfully.", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(" ", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<QuestionWrapper>> getAllQuestions(Long id) {
        try{
       Optional<Quiz> quiz = quizDao.findById(id);
       List<Question> questions = quiz.get().getQuestions();
       List<QuestionWrapper> questionWrapper = new ArrayList<>();
       for(Question i : questions){
           QuestionWrapper qW = new QuestionWrapper();
           qW.setId(i.getId());
           qW.setQuestionTitle(i.getQuestionTitle());
           qW.setOption1(i.getOption1());
           qW.setOption2(i.getOption2());
           qW.setOption3(i.getOption3());
           qW.setOption4(i.getOption4());
           questionWrapper.add(qW);
       }
       return new ResponseEntity<>(questionWrapper,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Integer> checkAnswersAndGetResult(Long id, List<Response> response) {
        try {
            Quiz quiz = quizDao.findById(id).get();
            List<Question> questions = quiz.getQuestions();
            int i = 0;
            int result = 0;
            for (Response respend : response) {
                if (questions.get(i).getRightAnswer().equals(respend.getResponse()))
                    result += 1;
                i++;
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(0,HttpStatus.BAD_REQUEST);

    }
}
