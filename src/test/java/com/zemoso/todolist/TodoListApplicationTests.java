package com.zemoso.todolist;

import com.zemoso.todolist.entity.Task;
import com.zemoso.todolist.repository.TaskRepository;
import com.zemoso.todolist.service.TaskNotFoundException;
import com.zemoso.todolist.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
 class TodoListApplicationTests {
//
//	@Test
//	void contextLoads() {
//	}
    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskRepository taskRepository;

    @Test
     void getByUsername(){
        List<Task> tasks=new ArrayList<>();
        tasks.add(new Task(1,"task1","complete java module",false,"john"));
        tasks.add(new Task(2,"task2","complete dbms module",false,"john"));
        when(taskRepository.findByUsername("john")).thenReturn(tasks);
        assertEquals(2,taskService.findByUsername("john").size());
    }

    @Test
     void saveTask(){
        Task task=new Task(1,"task1","complete java module",false,"john");
//        when(taskRepository.save(task));
        taskService.save(task);
        verify(taskRepository,times(1)).save(task);
    }

    @Test
     void deleteTask(){
        Task task=new Task(1,"task1","complete java module",false,"john");
        taskService.deleteById(1);
        verify(taskRepository,times(1)).deleteById(1);
    }
    @Test
     void getById(){
        Task task=new Task(2,"task2","complete dbms module",false,"john");
        when(taskRepository.findById(2)).thenReturn(Optional.of(task));
        taskService.findById(2);
        verify(taskRepository,times(1)).findById(2);
    }

    @Test
    void getByIdThrowingException(){
        Task task=new Task(2,"task2","complete dbms module",false,"john");

        when(taskRepository.findById(2)).thenReturn(Optional.empty());
       Throwable exception= assertThrows(TaskNotFoundException.class,()->taskService.findById(2));
       assertEquals("Did not find Task id - 2",exception.getMessage());
    }
}
