package com.quizapplicationproject.quizapplication.Controller;

import com.quizapplicationproject.quizapplication.Entity.Question;
import com.quizapplicationproject.quizapplication.Entity.QuestionWrapper;
import com.quizapplicationproject.quizapplication.Entity.Response;
import com.quizapplicationproject.quizapplication.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numQ,@RequestParam String title){
        return quizService.createQuiz(category,numQ,title);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteQuizByid(@PathVariable Long id){
        return quizService.deleteQuizById(id);
    }

    @GetMapping("{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Long id){
        return quizService.getAllQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> checkQuizAnswer(@PathVariable Long id, @RequestBody List<Response> response){
        return quizService.checkAnswersAndGetResult(id,response);
    }
}
