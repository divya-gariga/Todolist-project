package com.zemoso.todolist.service;

import com.zemoso.todolist.entity.Task;
import com.zemoso.todolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImp implements TaskService{
    private TaskRepository taskRepository;
    @Autowired
    public TaskServiceImp(TaskRepository theTaskRepository){
        taskRepository=theTaskRepository;
    }
    @Override
    public List<Task> findByUsername(String user) {
        return taskRepository.findByUsername(user);
    }

    @Override
    public void save(Task theTask) {
         taskRepository.save(theTask);
    }

    @Override
    public Task findById(int theId) {
        Optional<Task> result = taskRepository.findById(theId);

        Task theTask = null;

        if (result.isPresent()) {
            theTask = result.get();
        }
        else {
            throw new TaskNotFoundException("Did not find Task id - " + theId);
        }

        return theTask;
    }

    @Override
    public void deleteById(int theId) {
        taskRepository.deleteById(theId);
    }
}
