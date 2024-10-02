package org.example.fido2.controller;

import org.example.fido2.domain.dto.UserResponseBean;
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

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable UUID id) {
        try{
            return ResponseEntity.ok(userService.getById(id));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        try{
            return ResponseEntity.ok(userService.getByUsername(username));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
    @GetMapping("/myFriends")
    public List<UserResponseBean> getMyFriends(){
        return userService.getMyFriends();
    }
    @GetMapping("/friendsOf/{username}")
    public List<UserResponseBean> getFriendsOf(@PathVariable String username){
        return userService.getFriendsOf(username);
    }

    @DeleteMapping("/friends")
    private ResponseEntity<?> deleteFriend(@RequestParam String username) {
        try{
            userService.deleteFriend(username);
            return ResponseEntity.ok().body(String.format("Friend [%s] is successfully deleted", username));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping
    private ResponseEntity<?> update(@RequestBody User user) {
        try{
            userService.update(user);
            return ResponseEntity.ok("User is successfully updated");
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}



