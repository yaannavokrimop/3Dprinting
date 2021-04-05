package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MainController {


//    private final UserRepo userRepo;


//    @GetMapping(value = "/")
//    public String greeting() {
//        return "";
//    }



//    @GetMapping("/main")
//    public List<User> main(
//            @AuthenticationPrincipal User user, Map<String, Object> model
//    ) {
//        model.put("message", user.getName());
//        return userRepo.findAll();
//    }
}
