package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.UserRepo;
import com.netcracker.educ.printing.security.UserDetailsImpl;
import com.netcracker.educ.printing.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserRestController {

    private UserRepo userRepo;
    private UserService userService;

    @Autowired
    public UserRestController(UserRepo userRepo,UserService userService) {
        this.userRepo = userRepo;
        this.userService=userService;
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

    @GetMapping("/executors")
    public List<User> listExecutors(){
        return userService.findAll();
    }

    @PostMapping("/update/{id}")
    public User updateProfile(@RequestBody User user,@PathVariable("id") User dbUser){
        log.info("User: "+user.toString()+";    dbUser: "+dbUser.toString());
        BeanUtils.copyProperties(user,dbUser,"id");
        return userService.updateUser(dbUser);
    }





}
