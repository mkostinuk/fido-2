package org.example.fido2.controller;

import org.example.fido2.domain.model.User;
import org.example.fido2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable UUID id) {
        try{
            return ResponseEntity.ok(userService.getById(id));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        try{
            return ResponseEntity.ok(userService.getByUsername(username));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    @PutMapping("/addFriend")
    public ResponseEntity<?> addFriend(@RequestParam String username){
        try{
            userService.addFriend(username);
            return ResponseEntity.ok().body(String.format("Friend [%s] is successfully added", username));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}



