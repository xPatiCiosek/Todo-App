package com.example.springboottodoapp.controllers;

import com.example.springboottodoapp.models.TodoItem;
import com.example.springboottodoapp.services.TodoItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TodoFormController {

    @Autowired
    private TodoItemService todoItemService;

    @GetMapping("/create-todo")
    public String showCreateForm(TodoItem todoItem){
        return "new-todo-item";
    }

    @PostMapping("/todo")
    public String createTodoItem(@Valid TodoItem todoItem, BindingResult result, Model model){
        TodoItem item = new TodoItem();
        item.setDescription(todoItem.getDescription());
        item.setIsComplete(todoItem.getIsComplete());

        todoItemService.save(todoItem);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model){
        TodoItem todoItem = todoItemService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("TodoItem id: "+id+" not found"));

        model.addAttribute("todo", todoItem);
        return "edit-todo-item";
    }

    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable("id") Long id, Model model ){
        TodoItem todoItem = todoItemService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("TodoItem id: "+id+" not found"));

        todoItemService.delete(todoItem);
        return "redirent:/";
    }

}
