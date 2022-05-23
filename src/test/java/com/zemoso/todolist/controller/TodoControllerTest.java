package com.zemoso.todolist.controller;

import com.zemoso.todolist.entity.Task;
import com.zemoso.todolist.service.TaskService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@SpringBootTest
@AutoConfigureMockMvc

class TodoControllerTest {
    @Autowired
     TodoController controller;

    @Autowired
    private MockMvc mvc;
    @MockBean
    private TaskService service;
    private Model model;

    @Test
    @WithMockUser(username="john",roles={"USER"})
    void getTasks() throws Exception {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1, "task1", "complete java module", false, "john"));
        tasks.add(new Task(2, "task2", "complete dbms module", false, "john"));
        when(service.findByUsername("john")).thenReturn(tasks);
        mvc.perform(get("/"))
                .andExpect(status().is(200))
                .andExpect(view().name("home"))
                .andExpect(model().attribute("tasks",hasSize(2)));
    }

    @Test
    @WithMockUser(username="john",roles={"USER"})
    void showFormForAdd() throws Exception {
        Task theTask=new Task();
        mvc.perform(get("/showFormForAdd"))
                .andExpect(status().is(200))
                .andExpect(view().name("add-task-form"))
                .andExpect(model().attribute("tasks",theTask));
    }
    @Test
    void saveTask(){
        Task task=new Task(1,"task1","complete java module",false,"john");
        service.save(task);
        verify(service,times(1)).save(task);
    }

    @Test
    @WithMockUser(username="john",roles={"USER"})
    void deleteTask() throws Exception {
        Task task=new Task(1,"task1","complete java module",false,"john");
        service.deleteById(1);
        verify(service,times(1)).deleteById(1);
        mvc.perform(get("/delete").param("taskId", String.valueOf(1)))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/"));
    }
    @Test
    void getById(){
        Task task=new Task(2,"task2","complete dbms module",false,"john");
        when(service.findById(2)).thenReturn(task);
        service.findById(2);
        verify(service,times(1)).findById(2);
    }
}