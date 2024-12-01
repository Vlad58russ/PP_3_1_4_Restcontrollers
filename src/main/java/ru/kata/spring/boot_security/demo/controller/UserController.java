package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepositories;


@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepositories userRepositories;

    @Autowired
    public UserController(UserRepositories userRepositories) {
        this.userRepositories = userRepositories;
    }

    @GetMapping()
    public String getUserProfile(Model model, @AuthenticationPrincipal UserDetails principal) {
        User user = userRepositories.findByUsername(principal.getUsername());
        model.addAttribute("currentUser", user);
        return "userInfo";
    }
}