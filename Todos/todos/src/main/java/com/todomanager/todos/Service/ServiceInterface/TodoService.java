package com.todomanager.todos.Service.ServiceInterface;

import com.todomanager.todos.DTO.TodoDTO;
import com.todomanager.todos.Entity.TodoEntity;

public interface TodoService {


    public TodoDTO createTodo(TodoDTO todo, Long ProfileId);
    public Boolean deleteTodo(Long id);
    public TodoDTO getTodoById(Long id);
    public TodoDTO updateTodo(TodoDTO updateTodo, Long id);
}
