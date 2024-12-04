package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getAllUsersTable(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("users", userService.showAllUser());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("currentUser", user);
        return "users";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "users";
    }

    @GetMapping("/new")
    public String newUser(ModelMap model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("currentUser", user);
        return "new";
    }

    @GetMapping("/{id}/update")
    public String updateUser(ModelMap model, @PathVariable("id") Long id) {
        model.addAttribute("one", userService.getUserById(id));
        return "users";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user, ModelMap model) {
        model.addAttribute("roles", roleService.getAllRoles());
        userService.save(user);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userService.update(id , user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
