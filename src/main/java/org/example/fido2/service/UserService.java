package org.example.fido2.service;

import org.example.fido2.security.UserDetailsImpl;
import org.example.fido2.dao.UserRepository;
import org.example.fido2.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    public User getById(UUID id){
        User user = repository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(String.format("User with id [%s] does not exist", id)));
        return user;
    }



    public List<User> getAllUsers() {
       return repository.findAll();
    }

    public User getByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(()->
                new IllegalArgumentException(String.format("User with username [%s] does not exist", username)));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Username [%s] not found", username)));
        return UserDetailsImpl.build(user);
    }


    public void addFriend(String username) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = getByUsername(name);
        User friend = getByUsername(username);
        if(user.getFriends().contains(friend)){
            throw new IllegalArgumentException("This user is already your friend");
        }
        user.getFriends().add(friend);
        repository.save(user);
    }
}
