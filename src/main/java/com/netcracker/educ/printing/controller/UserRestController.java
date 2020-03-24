package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserRestController {

    private UserRepo repo;

    @Autowired
    public UserRestController(UserRepo repo) {
        this.repo = repo;
    }



    @GetMapping
    public User getCurrentUser(@AuthenticationPrincipal User user){
        log.info("This user "+user.getEmail()+" in his profile");
        return user;
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable("id") User user){
        log.info("This user "+user.getEmail()+" in his profile");
        return user;
    }


}
