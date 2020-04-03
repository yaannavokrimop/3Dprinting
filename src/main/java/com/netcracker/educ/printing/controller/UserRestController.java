package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.UserRepo;
import com.netcracker.educ.printing.security.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserRestController {

    private UserRepo userRepo;

    @Autowired
    public UserRestController(UserRepo repo) {
        this.userRepo = repo;
    }


    @GetMapping()
    public User getCurrentUser(){
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepo.findByEmail(principal.getEmail());
        log.info("This user "+user.getEmail()+" in his profile");
        return user;
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable("id") User user){
        log.info("This user "+user.getEmail()+" in his profile");
        return user;
    }


}
