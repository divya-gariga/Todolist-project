package com.zemoso.todolist.controller;

import com.zemoso.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @Autowired
    private TaskService taskService;
    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }
}
