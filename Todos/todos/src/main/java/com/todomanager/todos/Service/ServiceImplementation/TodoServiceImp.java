package com.todomanager.todos.Service.ServiceImplementation;

import com.todomanager.todos.DTO.TodoDTO;
import com.todomanager.todos.Entity.ProfileEntity;
import com.todomanager.todos.Entity.TodoEntity;
import com.todomanager.todos.Repository.ProfileRepository;
import com.todomanager.todos.Repository.TodoRepository;
import com.todomanager.todos.Service.ServiceInterface.TodoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImp implements TodoService {

    private ProfileRepository profileRepo;
    private TodoRepository todoRepo;
    private ModelMapper modelMapper;

    public TodoServiceImp(ProfileRepository profileRepo, TodoRepository todoRepo, ModelMapper modelMapper) {
        this.profileRepo = profileRepo;
        this.todoRepo = todoRepo;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public TodoDTO createTodo(TodoDTO todo, Long ProfileId){
        ProfileEntity profile = profileRepo.findById(ProfileId).orElseThrow();

        TodoEntity newtodo = toEntity(todo);

        newtodo.setProfile(profile);
        profile.getTodo().add(newtodo);  // only for consistency.

        TodoEntity newTodoEntity =todoRepo.save(newtodo);
        return toDTO(newTodoEntity);
    }

    @Transactional
    @Override
    public Boolean deleteTodo(Long id){
        try {
            todoRepo.getTodoAndDeleteById(id);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
        };
    return false;
    }

    public TodoDTO getTodoById(Long id){
       return toDTO(todoRepo.getTodoById(id));
    }

    @Transactional
    @Override
    public TodoDTO updateTodo(TodoDTO updateTodo, Long id){
        TodoEntity todo = todoRepo.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("todo is not exist with todoId "+id)
        );
        todo.setContent(updateTodo.getContent());
        todo.setTopic(updateTodo.getTopic());

        return toDTO(todo);
    }



    public TodoEntity toEntity(TodoDTO tododto){
        return modelMapper.map(tododto, TodoEntity.class);
    }

    public TodoDTO toDTO(TodoEntity todoEntity){
        return modelMapper.map(todoEntity, TodoDTO.class);
    }
}
