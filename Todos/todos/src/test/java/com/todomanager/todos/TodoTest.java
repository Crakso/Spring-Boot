package com.todomanager.todos;

import com.todomanager.todos.DTO.TodoDTO;
import com.todomanager.todos.Service.ServiceImplementation.TodoServiceImp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TodoTest {

    @Autowired
    private TodoServiceImp todoService;


    @Test
    public void createTodoTest(){
        TodoDTO todo = new TodoDTO();
        todo.setTopic("Buying Carrot from market");
        todo.setContent("I have to buy 6kg Carrot and 2kg of Mava for gajar ka halwa.");

        todoService.createTodo(todo,1L);

    }

    @Test
    public void updateTodoTest(){
        TodoDTO todo = new TodoDTO();
        todo.setTopic("kabaad");
        todo.setContent("Kabaad dalke aana tha. ");

        todoService.updateTodo(todo,2L);
    }

    @Test
    public void getTodoById(){
        System.out.println( todoService.getTodoById(2L));
    }

}
