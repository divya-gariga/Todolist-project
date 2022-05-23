package com.zemoso.todolist.controller;

import com.zemoso.todolist.entity.Task;
import com.zemoso.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
public class TodoController {
    @Autowired
    private TaskService taskService;
    private String tasks="tasks";
    @GetMapping("/")
    public String showHomePage(@CurrentSecurityContext(expression="authentication?.name") String user,Model theModel) {
        List<Task> theTasks=taskService.findByUsername(user);
        theModel.addAttribute(tasks,theTasks);
        theModel.addAttribute("user_name",user);
        return "home";
    }
    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel){
        Task theTask=new Task();
        theModel.addAttribute(tasks,theTask);
        return "add-task-form";
    }

    @PostMapping("/saveTask")
    public String saveTask(@CurrentSecurityContext(expression="authentication?.name") String user,
                           @ModelAttribute("tasks")  @Valid Task theTask,
                           BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            return "/add-task-form";
        }
        else{
            theTask.setUsername(user);
            taskService.save(theTask);
            return "redirect:/";
        }
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("taskId") int theId,
                                    Model theModel) {
        Task theTask = taskService.findById(theId);

        // set task as a model attribute to pre-populate the form
        theModel.addAttribute(tasks, theTask);

        // send over to our form
        return "/add-task-form";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam("taskId") int theId) {
        taskService.deleteById(theId);
        return "redirect:/";

    }

}
