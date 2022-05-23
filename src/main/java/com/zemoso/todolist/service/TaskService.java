package com.zemoso.todolist.service;

import com.zemoso.todolist.entity.Task;

import java.util.List;

public interface TaskService {

  public  List<Task> findByUsername(String user);

  public void save(Task theTask);

  public Task findById(int theId);

 public  void deleteById(int theId);
}
