package com.example.my_kursach.controllers;

import com.example.my_kursach.models.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("/")
    public String greeting(Model model)
    {
        model.addAttribute("title", "Добро пожаловать!");
        return "main";
    }
    @GetMapping("/mylogin")
    public String login(Model model)
    {
        model.addAttribute("title", "Вход в систему");
        return "login";
    }
    @GetMapping("/user/")
    public String getUser(Model model, @AuthenticationPrincipal User user)
    {
        model.addAttribute("login",user.getUsername());
        return "user";
    }
    @GetMapping("/admin/")
    public String getAdmin(Model model, @AuthenticationPrincipal User user)
    {
        model.addAttribute("login",user.getUsername());
        model.addAttribute("tittle","Страница администратора");
        return "admin";
    }
    @GetMapping("/supplier/")
    public String getSupplier(Model model, @AuthenticationPrincipal User user)
    {
        model.addAttribute("login",user.getUsername());
        model.addAttribute("tittle","Страница поставщика");
        return "supplier";
    }
    @GetMapping("/auth")
    public String auth(Model model)
    {
        model.addAttribute("tittle","Регистрация в системе");
        return "auth";
    }
}
