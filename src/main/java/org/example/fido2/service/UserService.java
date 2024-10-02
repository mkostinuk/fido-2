package org.example.fido2.service;

import org.example.fido2.domain.dto.UserResponseBean;
import org.example.fido2.domain.model.Role;
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
    public UserResponseBean getById(UUID id){
        User user = repository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(String.format("User with id [%s] does not exist", id)));
        return mapUserResponse(user);
    }
    private UserResponseBean mapUserResponse(User user){
        return UserResponseBean.builder()
                .username(user.getUsername())
                .name(user.getName())
                .age(user.getAge()).build();
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User findByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(()->
                new IllegalArgumentException(String.format("User with username [%s] does not exist", username)));
    }
    public UserResponseBean getByUsername(String username) {
        return mapUserResponse(findByUsername(username));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Username [%s] not found", username)));
        return UserDetailsImpl.build(user);
    }


    public void addFriend(String username) {
        User user = findByUsername(getUsernameAuthenticatedUser());
        User friend = findByUsername(username);
        if(user.getFriends().contains(friend)){
            throw new IllegalArgumentException("This user is already your friend");
        }
        user.getFriends().add(friend);
        repository.save(user);
    }
    private String getUsernameAuthenticatedUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public List<UserResponseBean> getMyFriends() {
        User user = findByUsername(getUsernameAuthenticatedUser());
        return user.getFriends().stream().map(this::mapUserResponse).toList();
    }

    public void delete(UUID id) {
        repository.findById(id).ifPresentOrElse(repository::delete, () -> {
            throw new IllegalArgumentException(String.format("User with id [%s] does not exist", id));
        });
    }

    public void changeRole(UUID id, String role) {
        repository.findById(id).ifPresentOrElse(user -> {
            user.setRole(Role.valueOf(role));
            repository.save(user);
        }, () -> {
            throw new IllegalArgumentException(String.format("User with id [%s] does not exist", id));
        });
    }

    public void deleteFriend(String username) {
        User user = findByUsername(getUsernameAuthenticatedUser());
        User friend = findByUsername(username);
        if(!user.getFriends().contains(friend)){
            throw new IllegalArgumentException("This user is not your friend");
        }
        user.getFriends().remove(friend);
        repository.save(user);
    }

    public List<UserResponseBean> getFriendsOf(String username) {
        return findByUsername(username).getFriends().stream().map(this::mapUserResponse).toList();
    }

    public void update(User user) {
        User myUser = findByUsername(getUsernameAuthenticatedUser());
        if(user.getUsername() != null){
            repository.findByUsername(user.getUsername()).ifPresent(user1 -> {
                throw new IllegalArgumentException(String.format("Username [%s] is already taken", user.getUsername()));
            });
            myUser.setUsername(user.getUsername());
        }
        if(user.getName() != null){
            myUser.setName(user.getName());
        }
        if(user.getPassword() != null){
            myUser.setPassword(user.getPassword());
        }
        if(user.getAge() != 0){
            myUser.setAge(user.getAge());
        }
        repository.save(myUser);

    }
}
