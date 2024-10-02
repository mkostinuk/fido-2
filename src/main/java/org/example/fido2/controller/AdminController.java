package org.example.fido2.controller;

import org.example.fido2.domain.model.User;
import org.example.fido2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;
    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/changeRole")
    public ResponseEntity<?> changeRole(@RequestParam UUID id, @RequestParam String role){
        try {
            userService.changeRole(id, role);
            return ResponseEntity.ok().body(String.format("Role of user with id [%s] is successfully changed", id));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        try{
            userService.delete(id);
            return ResponseEntity.ok().body(String.format("User with id [%s] is successfully deleted", id));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
