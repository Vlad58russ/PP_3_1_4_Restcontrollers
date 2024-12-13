package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.showAllUser();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles() {
        List<Role> roles = roleService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addUser(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> editUser(@RequestBody User user) {
        userService.update(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
