package com.todomanager.todos.Controller;

import com.todomanager.todos.DTO.TodoDTO;
import com.todomanager.todos.Entity.TodoEntity;
import com.todomanager.todos.Service.ServiceImplementation.TodoServiceImp;
import com.todomanager.todos.Service.ServiceInterface.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService){
        this.todoService=todoService;
    }


    @GetMapping("/{id}")
    private ResponseEntity<TodoDTO> getTodoById(@PathVariable Long id){
       TodoDTO todo = todoService.getTodoById(id);
       return ResponseEntity.ok(todo);
    }

    @PostMapping("/create-todo/{profile_id}")
    private ResponseEntity<TodoDTO> createTodo(@RequestBody TodoDTO todo,@PathVariable Long profile_id){
        TodoDTO newtodo = todoService.createTodo(todo,profile_id);
       return ResponseEntity.status(HttpStatus.CREATED).body(newtodo);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteTodo(@PathVariable Long id){
       if(todoService.deleteTodo(id))
        return ResponseEntity.ok("Todo is deleted successfully");
       return ResponseEntity.badRequest().body("Something went wrong. Please try again");
    }

    @PutMapping("/{id}")
    private ResponseEntity<TodoDTO> updateTodo(@PathVariable Long id, @RequestBody TodoDTO todoDTO){
        TodoDTO updateTodo = todoService.updateTodo(todoDTO,id);
    return ResponseEntity.ok(updateTodo);
    }

}
